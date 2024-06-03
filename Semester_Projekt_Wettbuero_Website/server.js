async function fetchUser() {
    try {
        const response = await fetch('http://localhost:8080/users/topUsers');
        if (!response.ok) {
            throw new Error('Network response was not ok!');
        }

        const users = await response.json();
        storeUsers(users);
    } catch (error) {
        console.error('Failed to fetch users:', error);
    }
}

async function fetchUpcomming() {
    try {
        const response = await fetch('http://localhost:8080/horserace');
        const response1 = await fetch('http://localhost:8080/dograce');
        const response2 = await fetch('http://localhost:8080/snailrace');
        if (!response.ok || !response1.ok || !response2.ok) {
            throw new Error('Network response was not ok!');
        }

        const horseraces = await response.json();
        const dograces = await response1.json();
        const snailraces = await response2.json();
        const tmp = [];
        const list = [];

        tmp.push(...horseraces);
        tmp.push(...dograces);
        tmp.push(...snailraces);

        tmp.forEach(element => {
            if (element.status === "UPCOMING") {
                list.push(element);
            }
        });

        console.log(list);

        storeRaces(list);
    } catch (error) {
        console.error('Failed to fetch users:', error);
    }
}

function storeUsers(users) {
    const userJSON = JSON.stringify(users);
    localStorage.setItem('users', userJSON);
}
function storeRaces(horserace) {
    const horseraceJSON = JSON.stringify(horserace);
    localStorage.setItem('horserace', horseraceJSON);
}

function getStoredUsers() {
    const userJSON = localStorage.getItem('users');

    if (userJSON) {
        return JSON.parse(userJSON);
    }
    return [];
}
function getStoredUpcomming() {
    const horseraceJSON = localStorage.getItem('horserace');

    if (horseraceJSON) {
        return JSON.parse(horseraceJSON);
    }
    return [];
}

function loadData() {
    const users = getStoredUsers();  // Correct function name
    const playerFirst = document.querySelector('.player-first');
    const playerSecond = document.querySelector('.player-second');
    const playerThird = document.querySelector('.player-third');
    const playerFourth = document.querySelector('.player-fourth');
    const playerFifth = document.querySelector('.player-fifth');

    if (users.length > 0) {
        playerFirst.textContent = users[0] ? users[0].username : 'N/A';
        playerSecond.textContent = users[1] ? users[1].username : 'N/A';
        playerThird.textContent = users[2] ? users[2].username : 'N/A';
        playerFourth.textContent = users[3] ? users[3].username : 'N/A';
        playerFifth.textContent = users[4] ? users[4].username : 'N/A';
    }
}
function loadDataHorses() {
    const horses = getStoredUpcomming();  // Correct function name
    const hFirst = document.querySelector('.h-first');
    const hSecond = document.querySelector('.h-second');
    const hThird = document.querySelector('.h-third');
    const hFourth = document.querySelector('.h-fourth');
    const hFifth = document.querySelector('.h-fifth');

    console.log(horses);

    if (horses.length > 0) {
        hFirst.textContent = horses[0] ? horses[0].name + ": " + horses[0].location  : 'N/A';
        hSecond.textContent = horses[1] ? horses[1].name + ": " + horses[1].location: 'N/A';
        hThird.textContent = horses[2] ? horses[2].name + ": " + horses[2].location: 'N/A';
        hFourth.textContent = horses[3] ? horses[3].name + ": " + horses[3].location: 'N/A';
        hFifth.textContent = horses[4] ? horses[4].name + ": " + horses[4].location : 'N/A';
    }
}

document.addEventListener("DOMContentLoaded", fetchUser);
document.addEventListener("DOMContentLoaded", fetchUpcomming);
document.addEventListener("DOMContentLoaded", loadData);
document.addEventListener("DOMContentLoaded", loadDataHorses);
