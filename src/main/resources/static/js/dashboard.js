let stompClient;
const historyBody = document.getElementById('history-body');

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        stompClient.subscribe('/topic/results', (message) => {
            const data = JSON.parse(message.body);
            updateLiveCard(data);
            addToHistory(data);
        });
    });
}

function updateLiveCard(data) {
    document.getElementById('pilot-name').textContent = `ПІЛОТ ${data.pilotId}`;
    const statusEl = document.getElementById('status');
    statusEl.textContent = data.polarity;
    statusEl.className = `px-6 py-2 rounded-full text-xl font-bold ${data.polarity === 'NEGATIVE' ? 'bg-rose-600' : data.polarity === 'POSITIVE' ? 'bg-emerald-600' : 'bg-amber-600'}`;

    document.getElementById('stress-level').textContent = data.stressLevel.toFixed(2);
    document.getElementById('polarity').textContent = data.polarity;
    document.getElementById('confidence').textContent = data.confidence.toFixed(2);
}

function addToHistory(data) {
    const row = document.createElement('tr');
    row.className = 'border-b border-slate-700 hover:bg-slate-800';
    row.innerHTML = `
        <td class="py-4">${new Date().toLocaleTimeString()}</td>
        <td class="py-4 font-medium">${data.pilotId}</td>
        <td class="py-4"><span class="px-3 py-1 rounded-full text-sm ${data.polarity === 'NEGATIVE' ? 'bg-rose-600' : data.polarity === 'POSITIVE' ? 'bg-emerald-600' : 'bg-amber-600'}">${data.polarity}</span></td>
        <td class="py-4">${data.stressLevel.toFixed(2)}</td>
        <td class="py-4 text-slate-400">${data.emotions}</td>
    `;
    historyBody.prepend(row);
}

window.onload = connect;