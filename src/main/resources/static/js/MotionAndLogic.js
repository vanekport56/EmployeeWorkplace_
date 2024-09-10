// MotionAndLogic.js

const canvas = document.getElementById('backgroundCanvas');
const ctx = canvas.getContext('2d');

// Изменение размеров canvas по размеру окна
function resizeCanvas() {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
}

window.addEventListener('resize', resizeCanvas);
resizeCanvas();

// Сгенерируем точки
let particles = [];
const particleCount = 100;

function initParticles() {
    particles = [];
    for (let i = 0; i < particleCount; i++) {
        particles.push({
            x: Math.random() * canvas.width,
            y: Math.random() * canvas.height,
            speedX: (Math.random() - 0.5) * 2,
            speedY: (Math.random() - 0.5) * 2,
            radius: Math.random() * 2 + 1
        });
    }
}

function drawParticles() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    particles.forEach(p => {
        // Рисуем точки
        ctx.beginPath();
        ctx.arc(p.x, p.y, p.radius, 0, Math.PI * 2);
        ctx.fillStyle = '#555';
        ctx.fill();

        // Движение точек
        p.x += p.speedX;
        p.y += p.speedY;

        // Отражение от краев экрана
        if (p.x < 0 || p.x > canvas.width) p.speedX *= -1;
        if (p.y < 0 || p.y > canvas.height) p.speedY *= -1;
    });
}

function animate() {
    drawParticles();
    requestAnimationFrame(animate);
}

initParticles();
animate();
