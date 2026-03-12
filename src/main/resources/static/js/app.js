// ── MediPlus app.js ──
const API = 'http://localhost:8080/api';
const OPENAI_API_KEY = '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'; 

/* ── SESSION HELPERS ── */
function getToken()    { return localStorage.getItem('mp_token'); }
function getUserName() { return localStorage.getItem('mp_name') || 'Paciente'; }
function getUserEmail(){ return localStorage.getItem('mp_email') || ''; }

function requireAuth() {
  if (!getToken()) { location.href = 'login.html'; return false; }
  return true;
}

async function authFetch(url, options = {}) {
  const token = getToken();
  options.headers = { 'Content-Type': 'application/json', ...(options.headers || {}) };
  if (token) options.headers['Authorization'] = `Bearer ${token}`;
  const res = await fetch(url, options);
  if (res.status === 401) { logout(); return null; }
  return res;
}

function logout() {
  localStorage.removeItem('mp_token');
  localStorage.removeItem('mp_name');
  localStorage.removeItem('mp_email');
  location.href = 'login.html';
}

/* ── SIDEBAR ── */
function renderSidebar(activePage) {
  const pages = [
    { id: 'dashboard',      icon: '⊞', label: 'Dashboard',         href: 'dashboard.html' },
    { id: 'profile',        icon: '👤', label: 'Pacientes',          href: 'profile.html' },
    { id: 'history',        icon: '📋', label: 'Historial Clínico',  href: 'history.html' },
    { id: 'diseases',       icon: '💊', label: 'Enfermedades',       href: 'diseases.html' },
    { id: 'appointments',   icon: '📅', label: 'Citas Médicas',      href: 'appointments.html' },
    { id: 'surgery',        icon: '🔬', label: 'Cirugías',           href: 'surgery.html' },
    { id: 'rehabilitation', icon: '🏃', label: 'Rehabilitación',     href: 'rehabilitation.html' },
    { id: 'nutrition',      icon: '🥗', label: 'Dieta y Nutrición',  href: 'nutrition.html' },
    { id: 'chat',           icon: '🤖', label: 'Asistente IA',       href: 'chat.html' },
  ];

  const navLinks = pages.map(p => `
    <a href="${p.href}" class="nav-link ${activePage === p.id ? 'active' : ''}">
      <span class="icon">${p.icon}</span>${p.label}
    </a>
  `).join('');

  const name    = getUserName();
  const email   = getUserEmail();
  const initials = name.split(' ').map(w => w[0]).join('').substring(0,2).toUpperCase();

  return `
    <div class="sidebar-logo">
      <img src="img/logo.jpeg" alt="MediPlus">
    </div>
    <span class="nav-section-label">Menú Principal</span>
    ${navLinks}
    <div class="sidebar-footer">
      <div style="display:flex;align-items:center;gap:10px;padding:12px;background:var(--surface2);border-radius:12px;border:1px solid var(--border);margin-bottom:10px;">
        <div style="width:36px;height:36px;border-radius:50%;background:var(--grad-main);display:flex;align-items:center;justify-content:center;font-weight:800;font-size:13px;color:white;flex-shrink:0;">${initials}</div>
        <div style="overflow:hidden;">
          <div style="font-size:13px;font-weight:700;color:var(--text);white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${name}</div>
          <div style="font-size:11px;color:var(--text-muted);white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${email}</div>
        </div>
      </div>
      <button onclick="logout()" class="nav-link" style="width:100%;border:none;cursor:pointer;background:none;text-align:left;">
        <span class="icon">↩</span> Cerrar sesión
      </button>
    </div>
  `;
}

/* ── TOAST ── */
function showToast(message, type = 'success') {
  let toast = document.getElementById('toast');
  if (!toast) {
    toast = document.createElement('div');
    toast.id = 'toast';
    document.body.appendChild(toast);
  }
  toast.className = `toast ${type}`;
  toast.innerHTML = `${type === 'success' ? '✓' : '✕'} ${message}`;
  toast.classList.add('show');
  setTimeout(() => toast.classList.remove('show'), 3500);
}

/* ── OPENAI ── */
async function askOpenAI(systemPrompt, userPrompt) {
  const response = await fetch('https://api.openai.com/v1/chat/completions', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${OPENAI_API_KEY}`
    },
    body: JSON.stringify({
      model: 'gpt-3.5-turbo',
      messages: [
        { role: 'system', content: systemPrompt },
        { role: 'user',   content: userPrompt }
      ],
      max_tokens: 700,
      temperature: 0.7
    })
  });
  if (!response.ok) throw new Error(`OpenAI error ${response.status}`);
  const data = await response.json();
  return data.choices[0].message.content;
}