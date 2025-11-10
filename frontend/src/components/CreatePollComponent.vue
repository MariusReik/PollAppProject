<script setup>
import { ref } from 'vue'
import api from '../api/api.js'

const question = ref('')
const options = ref([''])
const success = ref(false)
const error = ref(null)

function addOption() {
  options.value.push('')
}

function removeOption(index) {
  options.value.splice(index, 1)
}

async function createPoll() {
  error.value = null
  success.value = false

  try {
    const pollData = {
      question: question.value.trim(),
      options: options.value
          .filter(o => o.trim() !== '')
          .map(text => ({ text })),
    }

    if (!pollData.question || pollData.options.length < 2) {
      throw new Error('Please enter a question and at least two options.')
    }

    const response = await api.post('/polls', pollData)
    console.log('Poll created:', response.data)
    success.value = true
    question.value = ''
    options.value = ['']
  } catch (err) {
    console.error('Error creating poll:', err)
    error.value = err.response?.data || err.message
  }
}
</script>

<template>
  <div class="p-6">
    <h2 class="text-2xl font-bold mb-4">Create a Poll</h2>

    <form @submit.prevent="createPoll" class="space-y-4">
      <div>
        <label class="block text-sm font-medium">Question:</label>
        <input
            v-model="question"
            class="border rounded p-2 w-full"
            placeholder="Enter your question"
        />
      </div>

      <div v-for="(opt, index) in options" :key="index" class="flex gap-2">
        <input
            v-model="options[index]"
            class="border rounded p-2 flex-1"
            placeholder="Option text"
        />
        <button
            type="button"
            @click="removeOption(index)"
            class="text-red-500 hover:text-red-700"
        >
          ✕
        </button>
      </div>

      <div>
        <button
            type="button"
            @click="addOption"
            class="text-blue-600 hover:underline"
        >
          + Add Option
        </button>
      </div>

      <div>
        <button
            type="submit"
            class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Create Poll
        </button>
      </div>
    </form>

    <p v-if="success" class="text-green-600 mt-4">Poll created successfully!</p>
    <p v-if="error" class="text-red-600 mt-4">Error: {{ error }}</p>
  </div>
</template>
