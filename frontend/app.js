const API_URL = "http://localhost:8080/api/tasks";

const taskModal = document.getElementById("taskModal");
const openModalBtn = document.getElementById("openModalBtn");
const closeModalBtn = document.getElementById("closeModalBtn");
const taskForm = document.getElementById("taskForm");

// Modal Controls
openModalBtn.addEventListener("click", () => taskModal.classList.add("active"));
closeModalBtn.addEventListener("click", () => taskModal.classList.remove("active"));
taskModal.addEventListener("click", (e) => { if (e.target === taskModal) taskModal.classList.remove("active"); });

// Fetch Tasks
async function fetchTasks() {
    try {
        const response = await fetch(API_URL);
        const tasks = await response.json();
        renderBoard(tasks);
    } catch (error) {
        console.error("Error connecting to backend:", error);
    }
}

// Render dynamic playful layout cards
function renderBoard(tasks) {
    const columns = {
        "TO_DO": document.getElementById("TO_DO"),
        "IN_PROGRESS": document.getElementById("IN_PROGRESS"),
        "DONE": document.getElementById("DONE")
    };

    Object.values(columns).forEach(col => col.innerHTML = "");

    // Fun context icons arrays to cycle through
    const designIcons = ["📝", "🔍", "📄", "💡", "💻", "⚡", "🎨", "👥", "🚀", "📢"];

    tasks.forEach((task, index) => {
        const card = document.createElement("div");
        card.className = "task-card";
        card.draggable = true;
        card.dataset.id = task.id;
        card.dataset.status = task.status;

        // Pick a consistent fun icon based on task title index
        const cardIcon = designIcons[index % designIcons.length];

        // Use the description property as the priority field safely
        const priority = task.description || "Medium";

        // Generate right meta element: checkmark badge if "DONE", priority badge if otherwise
        const metaBadge = task.status === "DONE"
            ? `<div class="done-check-circle">✓</div>`
            : `<span class="priority-pill priority-${priority.toLowerCase()}">${priority}</span>`;

        card.innerHTML = `
            <div class="card-icon">${cardIcon}</div>
            <div class="card-body-content">
                <h3>${task.title}</h3>
                <div class="card-meta-row">
                    ${metaBadge}
                    <button class="btn-card-delete" onclick="deleteTask('${task.id}')">🗑️</button>
                </div>
            </div>
        `;

        card.addEventListener("dragstart", handleDragStart);
        card.addEventListener("dragend", handleDragEnd);
        columns[task.status].appendChild(card);
    });
}

// Create New Task Form Handler
taskForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const title = document.getElementById("taskTitle").value;
    const priority = document.getElementById("taskDescription").value; // Maps priority here

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ title, description: priority, status: "TO_DO" })
        });

        if (response.ok) {
            taskForm.reset();
            taskModal.classList.remove("active");
            fetchTasks();
        }
    } catch (error) {
        console.error("Failed to add task:", error);
    }
});

// Delete Task
window.deleteTask = async function(id) {
    if (!confirm("Delete this card?")) return;
    try {
        const response = await fetch(`${API_URL}/${id}`, { method: "DELETE" });
        if (response.ok) fetchTasks();
    } catch (error) {
        console.error("Failed to delete task:", error);
    }
};

// Drag and Drop Flow
let draggedCard = null;

function handleDragStart() {
    draggedCard = this;
    this.classList.add("dragging");
}

function handleDragEnd() {
    this.classList.remove("dragging");
    document.querySelectorAll(".task-list").forEach(list => list.classList.remove("drag-hover"));
    draggedCard = null;
}

document.querySelectorAll(".task-list").forEach(list => {
    list.addEventListener("dragover", (e) => {
        e.preventDefault();
        list.classList.add("drag-hover");
    });
    list.addEventListener("dragleave", () => list.classList.remove("drag-hover"));
    list.addEventListener("drop", async function(e) {
        e.preventDefault();
        const targetStatus = this.id;

        if (draggedCard && draggedCard.dataset.status !== targetStatus) {
            const taskId = draggedCard.dataset.id;
            const taskTitle = draggedCard.querySelector("h3").innerText;
            // Extract the priority pill text if it exists, otherwise fall back to default
            const priorityPill = draggedCard.querySelector(".priority-pill");
            const taskPriority = priorityPill ? priorityPill.innerText : "Medium";

            this.appendChild(draggedCard);
            draggedCard.dataset.status = targetStatus;

            try {
                await fetch(`${API_URL}/${taskId}`, {
                    method: "PUT",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ title: taskTitle, description: taskPriority, status: targetStatus })
                });
                fetchTasks();
            } catch (error) {
                console.error("Failed to update task state drop:", error);
            }
        }
    });
});

fetchTasks();