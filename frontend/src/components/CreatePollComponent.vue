<template>
  <section class="panel">
    <h2>Create Poll</h2>
    <form @submit.prevent="createPoll">
      <label>Question</label>
      <input v-model="question" placeholder="Enter your question" required />

      <label>Options</label>
      <div v-for="(opt, i) in options" :key="i" class="option">
        <input v-model="options[i]" placeholder="Option" required />
        <button type="button" @click="removeOption(i)">✕</button>
      </div>

      <button type="button" @click="addOption">Add Option</button>
      <button type="submit">Create</button>
    </form>
  </section>
</template>

<script setup>
import { ref, inject } from "vue";
const keycloak = inject("keycloak");
const question = ref("");
const options = ref([""]);

const addOption = () => options.value.push("");
const removeOption = (i) => options.value.splice(i, 1);

const createPoll = async () => {
  try {
    const token = keycloak?.token;
    const res = await fetch("http://localhost:8081/polls", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
      body: JSON.stringify({
        question: question.value,
        options: options.value.filter(o => o.trim() !== "")
      })
    });
    if (!res.ok) throw new Error("Failed to create poll");
    question.value = "";
    options.value = [""];
    alert("Poll created!");
  } catch (e) {
    console.error(e);
  }
};
</script>

<style>
.panel {
  border: 1px solid #ccc;
  padding: 16px;
  margin-bottom: 20px;
  border-radius: 8px;
}
.option {
  display: flex;
  align-items: center;
}
.option input {
  flex: 1;
  margin-right: 6px;
}
input {
  width: 100%;
  padding: 6px;
  margin-bottom: 10px;
}
</style>
