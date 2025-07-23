function addTask() {
  const task = document.getElementById("task").value.trim();
  const category = document.getElementById("category").value;
  const responseEl = document.getElementById("response");

  if (!task) {
    responseEl.innerText = "⚠️ Task cannot be empty.";
    return;
  }

  const data = {
    task: task,
    category: category
  };

  const xhr = new XMLHttpRequest();
  xhr.open("POST", "http://localhost:4000/task", true);
  xhr.setRequestHeader("Content-Type", "application/json");

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
      responseEl.innerText = "✅ Task added successfully!";
      document.getElementById("task").value = "";
    }
  };

  xhr.send(JSON.stringify(data));
}
