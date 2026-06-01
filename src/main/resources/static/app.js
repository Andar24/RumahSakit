function navigateTo(pageId, pushState = true) {
    document.querySelectorAll('.spa-page').forEach(el => el.classList.remove('active'));
    const targetPage = document.getElementById(pageId);
    if(targetPage) targetPage.classList.add('active');
    window.scrollTo({ top: 0, behavior: 'smooth' });
    if (pushState) history.pushState({ page: pageId }, "", "#" + pageId);
}

function toggleAuth(view) {
    document.getElementById('login-container').style.display = view === 'login' ? 'block' : 'none';
    document.getElementById('register-container').style.display = view === 'register' ? 'block' : 'none';
    document.getElementById('forgot-container').style.display = view === 'forgot' ? 'block' : 'none';
}

window.addEventListener('popstate', event => {
    event.state && event.state.page ? navigateTo(event.state.page, false) : navigateTo('view-beranda', false);
});

async function postData(url, data) {
    const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    });
    const result = await response.json();
    if (!response.ok) throw new Error(result.message);
    return result;
}

// ROUTING LOGIN TERBARU
document.getElementById('loginFormAPI')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const btn = document.getElementById('login-btn');
    btn.innerHTML = "Memproses...";
    try {
        const data = await postData('/api/login', {
            email: document.getElementById('login-email').value,
            password: document.getElementById('login-password').value
        });
        sessionStorage.setItem('userEmail', data.email);
        sessionStorage.setItem('userNama', data.nama);

        if (data.role === 'ADMIN') navigateTo('view-dashboard-admin');
        else if (data.role === 'DOKTER') navigateTo('view-dashboard-dokter');
        else if (data.role === 'POLI') navigateTo('view-dashboard-poli'); // ROUTING POLI BARU
        else navigateTo('view-dashboard-pasien');
    } catch (err) { alert("Gagal: " + err.message); }
    finally { btn.innerHTML = `Masuk Ke Akun <span class="material-symbols-outlined text-[24px]">arrow_forward</span>`; }
});

document.getElementById('registerFormAPI')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    try {
        await postData('/api/register', {
            nama: document.getElementById('reg-name').value,
            email: document.getElementById('reg-email').value,
            password: document.getElementById('reg-password').value
        });
        alert("Pendaftaran berhasil! Silakan masuk.");
        e.target.reset(); toggleAuth('login');
    } catch (err) { alert(err.message); }
});

document.getElementById('forgotFormAPI')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    try {
        const result = await postData('/api/reset-password', { email: document.getElementById('forgot-email').value });
        alert(result.message);
        e.target.reset(); toggleAuth('login');
    } catch (err) { alert(err.message); }
});

// FORM ADMIN: TAMBAH DOKTER, ADMIN, & POLI
document.getElementById('formAddDokter')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    try {
        await postData('/api/admin/add-dokter', { nama: document.getElementById('new-dokter-nama').value, email: document.getElementById('new-dokter-email').value, password: document.getElementById('new-dokter-pass').value });
        alert("Akun Dokter berhasil ditambahkan!"); e.target.reset();
    } catch (err) { alert(err.message); }
});

document.getElementById('formAddAdmin')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    try {
        await postData('/api/admin/add-admin', { nama: document.getElementById('new-admin-nama').value, email: document.getElementById('new-admin-email').value, password: document.getElementById('new-admin-pass').value });
        alert("Akun Admin berhasil ditambahkan!"); e.target.reset();
    } catch (err) { alert(err.message); }
});

document.getElementById('formAddPoli')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    try {
        await postData('/api/admin/add-poli', { nama: document.getElementById('new-poli-nama').value, email: document.getElementById('new-poli-email').value, password: document.getElementById('new-poli-pass').value });
        alert("Akun Staf Poli berhasil ditambahkan!"); e.target.reset();
    } catch (err) { alert(err.message); }
});

// UBAH SANDI DINAMIS
async function handleChangePassword(e, oldPassId, newPassId) {
    e.preventDefault();
    try {
        await postData('/api/change-password', { email: sessionStorage.getItem('userEmail'), oldPassword: document.getElementById(oldPassId).value, newPassword: document.getElementById(newPassId).value });
        alert("Kata sandi berhasil diubah!"); e.target.reset();
    } catch (err) { alert(err.message); }
}

document.getElementById('formChangePassDokter')?.addEventListener('submit', (e) => handleChangePassword(e, 'dokter-old-pass', 'dokter-new-pass'));
document.getElementById('formChangePassPasien')?.addEventListener('submit', (e) => handleChangePassword(e, 'pasien-old-pass', 'pasien-new-pass'));
document.getElementById('formChangePassPoli')?.addEventListener('submit', (e) => handleChangePassword(e, 'poli-old-pass', 'poli-new-pass'));

function logout() {
    if(confirm("Log keluar dari sistem?")) {
        sessionStorage.clear();
        document.getElementById('loginFormAPI')?.reset();
        navigateTo('view-auth');
    }
}

