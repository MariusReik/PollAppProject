<template>
  <div class="app">
    <header class="bar">
      <h1>PollApp</h1>
      <button @click="logout">Logout</button>
    </header>

    <main>
      <CreatePollComponent />
      <VoteComponent />
    </main>

    <footer class="foot">
      © 2025 PollApp
    </footer>
  </div>
</template>

<script setup>
import { inject } from "vue";
import CreatePollComponent from "./components/CreatePollComponent.vue";
import VoteComponent from "./components/VoteComponent.vue";

const keycloak = inject("keycloak");
const logout = () => keycloak.logout({ redirectUri: window.location.origin });
</script>

<style>
body {
  margin: 0;
  background: #f5f5f5;
  font-family: Arial, sans-serif;
}
.app {
  max-width: 900px;
  margin: 40px auto;
  background: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 24px;
}
.bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #ddd;
  padding-bottom: 12px;
}
button {
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 8px 12px;
  cursor: pointer;
}
button:hover {
  background-color: #0056b3;
}
main {
  margin-top: 24px;
}
.foot {
  border-top: 1px solid #ddd;
  text-align: center;
  color: #777;
  margin-top: 24px;
  padding-top: 8px;
}
</style>
