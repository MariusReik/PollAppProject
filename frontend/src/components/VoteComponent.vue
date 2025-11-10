<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/api.js'

const polls = ref([])
const selectedOptions = ref({})
const error = ref(null)
const success = ref(null)

async function fetchPolls() {
  try {
    const { data } = await api.get('/polls')
    polls.value = data
    console.log('Polls fetched:', data)
  } catch (err) {
    console.error('Error fetching polls:', err)
    error.value = err.response?.data || err.message
  }
}

async function vote(pollId, optionId) {
  try {
    await api.post(`/polls/${pollId}/vote`, { optionId })
    success.value = 'Vote submitted successfully!'
    await fetchPolls() // refresh results
  } catch (err) {
    console.error('❌ Error voting:', err)
    error.value = err.response?.data || err.message
  }
}

onMounted(() => {
  fetchPolls()
})
</script>

<template>
  <div class="p-6">
    <h2 class="text-2xl font-bold mb-4">Available Polls</h2>

    <div v-if="error" class="text-red-600 mb-4">{{ error }}</div>
    <div v-if="success" class="text-green-600 mb-4">{{ success }}</div>

    <div v-if="polls.length === 0">No polls available.</div>

    <ul>
      <li
          v-for="poll in polls"
          :key="poll.id"
          class="border p-4 mb-4 rounded shadow-sm"
      >
        <h3 class="font-semibold mb-2">{{ poll.question }}</h3>

        <ul class="space-y-2">
          <li
              v-for="option in poll.options"
              :key="option.id"
              class="flex justify-between items-center"
          >
            <span>{{ option.text }}</span>
            <button
                @click="vote(poll.id, option.id)"
                class="bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700"
            >
              Vote
            </button>
          </li>
        </ul>

        <div class="mt-3 text-sm text-gray-500">
          Total votes:
          {{ poll.options.reduce((sum, o) => sum + (o.votes || 0), 0) }}
        </div>
      </li>
    </ul>
  </div>
</template>
