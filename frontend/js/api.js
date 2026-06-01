// API модуль
const API = {
    BASE: '/api',

    request(url, method, data) {
        return $.ajax({
            url: `${this.BASE}${url}`,
            method: method,
            contentType: 'application/json',
            data: data ? JSON.stringify(data) : undefined,
            headers: this.getHeaders()
        });
    },

    getHeaders() {
        const headers = {};
        const token = localStorage.getItem('token');
        if (token) {
            headers['Authorization'] = `Bearer ${token}`;
        }
        return headers;
    },


    signUp(name, password) {
        return this.request('/auth/sign-up', 'POST', { name, password });
    },

    signIn(name, password) {
        return this.request('/auth/sign-in', 'POST', { name, password });
    },

    signOut() {
        return this.request('/auth/sign-out', 'POST');
    },

    // Получение текущего пользователя
    getCurrentUser() {
        console.log('getCurrentUser called');
        const result = this.request('/user/me', 'GET');
        console.log('request result:', result);
        console.log('result type:', typeof result);
        console.log('has then?', result && typeof result.then === 'function');
        console.log('is jQuery promise?', result && typeof result.promise === 'function');
        return result;
    }
};