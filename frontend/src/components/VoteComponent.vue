<script setup>
import { inject, ref, onMounted } from 'vue';

const keycloak = inject('keycloak');
const polls = ref([]);

async function fetchPolls() {
  try {
    // Ensure token is fresh
    await keycloak.updateToken(30);

    const response = await fetch(`${import.meta.env.VITE_API_URL}/polls`, {
      headers: {
        'Authorization': `Bearer ${keycloak.token}`,
        'Content-Type': 'application/json'
      }
    });

    if (!response.ok) throw new Error('Failed to fetch polls');
    polls.value = await response.json();
    console.log('Polls fetched:', polls.value);
  } catch (error) {
    console.error('Error fetching polls:', error);
  }
}

onMounted(() => {
  fetchPolls();
});
</script>

<template>
  <div class="p-6">
    <h2 class="text-2xl font-bold mb-4">Available Polls</h2>
    <div v-if="polls.length === 0">No polls available.</div>
    <ul>
      <li v-for="poll in polls" :key="poll.id" class="mb-2">
        <strong>{{ poll.question }}</strong>
        <ul class="ml-4 list-disc">
          <li v-for="option in poll.options" :key="option.id">
            {{ option.text }} ({{ option.votes }} votes)
          </li>
        </ul>
      </li>
    </ul>
  </div>
</template>
