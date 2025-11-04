<template>
  <section class="panel">
    <h2>Available Polls</h2>
    <div v-if="polls.length === 0">No polls available.</div>

    <div v-for="poll in polls" :key="poll.id" class="poll">
      <h3>{{ poll.question }}</h3>
      <div>
        <button v-for="opt in poll.options" :key="opt.id" @click="vote(poll.id, opt.id)">
          {{ opt.text }}
        </button>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, inject } from "vue";
const keycloak = inject("keycloak");
const polls = ref([]);

const fetchPolls = async () => {
  try {
    const token = keycloak?.token;
    const res = await fetch("http://localhost:8081/polls", {
      headers: { "Authorization": `Bearer ${token}` }
    });
    if (!res.ok) throw new Error("Failed to fetch polls");
    polls.value = await res.json();
  } catch (e) {
    console.error("Error fetching polls:", e);
  }
};

const vote = async (pollId, optionId) => {
  try {
    const token = keycloak?.token;
    const res = await fetch(`http://localhost:8081/polls/${pollId}/vote`, {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ optionId })
    });
    if (!res.ok) throw new Error("Vote failed");
    alert("Vote registered!");
    fetchPolls();
  } catch (e) {
    console.error("Error voting:", e);
  }
};

onMounted(fetchPolls);
</script>

<style>
.poll {
  border-bottom: 1px solid #ddd;
  padding: 10px 0;
}
button {
  margin-right: 6px;
}
</style>
