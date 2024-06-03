async function fetchUser() {
    const response = await fetch('http://localhost:8080/users/topUsers');

    if (!response.ok) {
        throw new Error('Network response was not ok!');
    }

    const users = await response.json();
    storeUsers(users);
}

function storeUsers(users) {
    const userJSON = JSON.stringify(users);
    localStorage.setItem('users', userJSON);
}

function getStoredUsers() {
    const userJSON = localStorage.getItem('users');

    if (userJSON) {
        return JSON.parse(userJSON);
    }
    return [];
}

function loadData() {
    const users = getStoredUser();
    const playerFirst = document.getElementById('player-first');
    const playerSecond = document.getElementById('player-second');
    const playerThird = document.getElementById('player-third');
    const playerFourt = document.getElementById('player-fourt');
    const playerFifth = document.getElementById('player-fifth');

    playerFirst.textContent = users[0];
    playerSecond.textContent = users[1];
    playerThird.textContent = users[2];
    playerFourt.textContent = users[3];
    playerFifth.textContent = users[4];            
}

document.addEventListener("DOMContentLoaded", fetchUser);
document.addEventListener("DOMContentLoaded", loadData);