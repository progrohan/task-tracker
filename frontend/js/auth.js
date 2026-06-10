const Auth = {
    check() {
        const token = localStorage.getItem('token');
        if (!token) {
            return Promise.resolve(false);
        }

        return API.getCurrentUser()
            .then(user => {
                this.currentUser = user;
                return true;
            })
            .catch(() => {
                this.logout();
                return false;
            });
    },

    login(name, password) {
        return API.signIn(name, password)
            .then(response => {
                localStorage.setItem('token', response.token);
                return API.getCurrentUser();
            })
            .then(user => {
                this.currentUser = user;
                return user;
            });
    },

    register(name, email, password) {
        return API.signUp(name, email, password);
    },

    logout() {
        return API.signOut()
            .always(() => {
                localStorage.removeItem('token');
                this.currentUser = null;
                App.navigate('login');
            });
    },

    currentUser: null
};