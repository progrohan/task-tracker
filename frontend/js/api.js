const API = {
    BASE: '/api',

    request(url, method, data) {
        const settings = {
            url: `${this.BASE}${url}`,
            method: method,
            contentType: 'application/json',
            headers: this.getHeaders()
        };

        if (data !== undefined) {
            settings.data = JSON.stringify(data);
        }

        return $.ajax(settings);
    },

    getHeaders() {
        const headers = {};
        const token = localStorage.getItem('token');
        if (token) {
            headers['Authorization'] = `Bearer ${token}`;
        }
        return headers;
    },

    // Auth
    signUp(name, password) {
        return this.request('/auth/sign-up', 'POST', { name, password });
    },

    signIn(name, password) {
        return this.request('/auth/sign-in', 'POST', { name, password });
    },

    signOut() {
        return this.request('/auth/sign-out', 'POST');
    },

    getCurrentUser() {
        return this.request('/user/me', 'GET');
    },

    // Tasks
    getTasksInProgress() {
        return this.request('/task/in-progress', 'GET');
    },

    getCompletedTasks() {
        return this.request('/task/completed', 'GET');
    },

    getTask(id) {
        return this.request(`/task/${id}`, 'GET');
    },

    createTask(title, description) {
        return this.request('/task', 'POST', { title, description });
    },

    updateTask(id, title, description) {
        return this.request(`/task/${id}`, 'PUT', { title, description });
    },

    completeTask(id) {
        return this.request(`/task/${id}`, 'PATCH');
    },

    deleteTask(id) {
        return this.request(`/task/${id}`, 'DELETE');
    }
};