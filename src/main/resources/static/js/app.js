const API = 'http://localhost:8080/api';
const OPENAI_API_KEY = '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';

function getToken()    { return localStorage.getItem('mp_token'); }
function getUserName() { return localStorage.getItem('mp_name') || 'Usuario'; }
function getUserEmail(){ return localStorage.getItem('mp_email') || ''; }

function requireAuth() {
  if (!getToken()) {
    location.href = 'login.html';
    return false;
  }
  return true;
}

function requirePassword() {
  localStorage.removeItem('mp_token');
  location.href = 'login.html';
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

function renderSidebar(activePage) {
  const name     = getUserName();
  const email    = getUserEmail();
  const initials = name.split(' ').map(w => w[0]).join('').substring(0,2).toUpperCase();

  const sections = [
    {
      label: 'Principal',
      items: [
        { id: 'dashboard', label: 'Dashboard',        href: 'dashboard.html' },
        { id: 'history',   label: 'Historial Clinico', href: 'history.html' },
        { id: 'profile',   label: 'Pacientes',         href: 'profile.html' },
      ]
    },
    {
      label: 'Modulos IA',
      items: [
        { id: 'nutrition',      label: 'Dieta y Nutricion', href: 'nutrition.html' },
        { id: 'rehabilitation', label: 'Rehabilitacion',    href: 'rehabilitation.html' },
        { id: 'chat',           label: 'Asistente Virtual', href: 'chat.html' },
      ]
    },
    {
      label: 'Clinico',
      items: [
        { id: 'appointments',  label: 'Citas Medicas',  href: 'appointments.html' },
        { id: 'diseases',      label: 'Enfermedades',   href: 'diseases.html' },
        { id: 'surgery',       label: 'Cirugias',       href: 'surgery.html' },
        { id: 'medications',   label: 'Medicamentos',   href: 'medications.html' },
      ]
    }
  ];

  const navHTML = sections.map(sec => `
    <div class="nav-section-label">${sec.label}</div>
    ${sec.items.map(item => `
      <a href="${item.href}" class="nav-link ${activePage === item.id ? 'active' : ''}">
        <span class="nav-dot"></span>
        ${item.label}
      </a>
    `).join('')}
  `).join('');

  return `
    <div class="sidebar-logo">
      <img src="img/logo.jpeg" alt="MediPlus"
        style="height:44px;object-fit:contain;display:block;"
        onerror="this.outerHTML='<div style=\'font-family:Fraunces,serif;font-size:20px;font-weight:700;color:var(--purple);font-style:italic;\'>MediPlus</div>'">
    </div>
    ${navHTML}
    <div class="sidebar-footer">
      <div style="width:34px;height:34px;border-radius:50%;background:var(--grad-brand);display:flex;align-items:center;justify-content:center;font-size:12px;font-weight:700;color:white;flex-shrink:0;">${initials}</div>
      <div style="overflow:hidden;flex:1;">
        <div style="font-size:12px;font-weight:700;color:var(--text);white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${name}</div>
        <div style="font-size:10px;color:var(--text-muted);white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${email}</div>
      </div>
      <button onclick="logout()" title="Cerrar sesion"
        style="background:none;border:none;cursor:pointer;color:var(--text-muted);font-size:18px;padding:4px;transition:color 0.2s;"
        onmouseover="this.style.color='var(--pink)'"
        onmouseout="this.style.color='var(--text-muted)'">&#x2192;</button>
    </div>
  `;
}

function showToast(message, type = 'success') {
  let toast = document.getElementById('toast');
  if (!toast) { toast = document.createElement('div'); toast.id = 'toast'; document.body.appendChild(toast); }
  toast.className = `toast ${type}`;
  toast.innerHTML = `${type === 'success' ? '&#10003;' : '&#10005;'} ${message}`;
  toast.classList.add('show');
  setTimeout(() => toast.classList.remove('show'), 3500);
}

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