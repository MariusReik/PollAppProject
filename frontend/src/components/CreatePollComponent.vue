<script setup>
import { ref, inject } from 'vue';

const keycloak = inject('keycloak');
const question = ref('');
const options = ref(['']);

function addOption() {
  options.value.push('');
}

function removeOption(index) {
  options.value.splice(index, 1);
}

async function createPoll() {
  try {
    await keycloak.updateToken(30);

    const pollData = {
      question: question.value,
      options: options.value.filter(o => o.trim() !== '').map(text => ({ text }))
    };

    const response = await fetch(`${import.meta.env.VITE_API_URL}/polls`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${keycloak.token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(pollData)
    });

    if (!response.ok) throw new Error('Failed to create poll');
    console.log('Poll created successfully');
  } catch (error) {
    console.error('Error creating poll:', error);
  }
}
</script>

<template>
  <div class="p-6">
    <h2 class="text-2xl font-bold mb-4">Create a Poll</h2>
    <form @submit.prevent="createPoll">
      <div class="mb-4">
        <label class="block text-sm font-medium">Question:</label>
        <input v-model="question" class="border p-2 w-full" placeholder="Enter your question" />
      </div>

      <div v-for="(opt, index) in options" :key="index" class="mb-2 flex">
        <input v-model="options[index]" class="border p-2 flex-1" placeholder="Option text" />
        <button type="button" @click="removeOption(index)" class="ml-2 text-red-500">Remove</button>
      </div>

      <button type="button" @click="addOption" class="text-blue-500 mb-4">Add Option</button>
      <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded">Create Poll</button>
    </form>
  </div>
</template>
