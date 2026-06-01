// Главный модуль приложения
const App = {
    init() {
        this.checkAuth();
    },

    checkAuth() {
        Auth.check()
            .then(isAuth => {
                if (isAuth) {
                    this.navigate('home');
                } else {
                    this.navigate('login');
                }
            });
    },

    navigate(page) {
        switch(page) {
            case 'home':
                this.renderHome();
                break;
            case 'login':
                this.renderLogin();
                break;
            case 'register':
                this.renderRegister();
                break;
        }
    },

    renderHome() {
        const user = Auth.currentUser;
        const html = `
            <div class="container">
                <div class="home-page">
                    <h1>Добро пожаловать, ${user.name}!</h1>
                    <p>Вы авторизованы</p>
                    <button class="btn btn-danger" id="logout-btn">Выйти</button>
                </div>
            </div>
        `;

        $('#app').html(html);
        $('#logout-btn').click(() => Auth.logout());
    },

    renderLogin() {
        const html = `
            <div class="form-page">
                <h2>Вход</h2>
                <div class="error" id="login-error"></div>
                <form id="login-form">
                    <div class="form-group">
                        <label>Имя пользователя</label>
                        <input type="text" id="login-name" required>
                    </div>
                    <div class="form-group">
                        <label>Пароль</label>
                        <input type="password" id="login-password" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Войти</button>
                </form>
                <p style="text-align: center;">
                    Нет аккаунта? <span class="link" id="to-register">Зарегистрироваться</span>
                </p>
            </div>
        `;

        $('#app').html(html);

        $('#login-form').submit(e => {
            e.preventDefault();
            const name = $('#login-name').val();
            const password = $('#login-password').val();

            Auth.login(name, password)
                .then(user => {
                    Auth.currentUser = user;
                    this.navigate('home');
                })
                .catch(xhr => {
                    const msg = xhr.responseJSON?.message || 'Ошибка входа';
                    $('#login-error').text(msg).show();
                });
        });

        $('#to-register').click(() => this.navigate('register'));
    },

    renderRegister() {
        const html = `
            <div class="form-page">
                <h2>Регистрация</h2>
                <div class="error" id="register-error"></div>
                <form id="register-form">
                    <div class="form-group">
                        <label>Имя пользователя</label>
                        <input type="text" id="register-name" required>
                    </div>
                    <div class="form-group">
                        <label>Пароль</label>
                        <input type="password" id="register-password" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
                </form>
                <p style="text-align: center;">
                    Уже есть аккаунт? <span class="link" id="to-login">Войти</span>
                </p>
            </div>
        `;

        $('#app').html(html);

        $('#register-form').submit(e => {
            e.preventDefault();
            const name = $('#register-name').val();
            const password = $('#register-password').val();

            Auth.register(name, password)
                .catch(xhr => {
                    const msg = xhr.responseJSON?.message || 'Ошибка регистрации';
                    $('#register-error').text(msg).show();
                });
        });

        $('#to-login').click(() => this.navigate('login'));
    }
};


$(document).ready(() => App.init());