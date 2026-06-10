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
                <header class="header">
                    <h1>Менеджер задач</h1>
                    <div class="user-info">
                        <span>${user.name}</span>
                        <button class="btn btn-logout" id="logout-btn">Выйти</button>
                    </div>
                </header>
                
                <div class="add-task">
                    <input type="text" id="new-task-title" placeholder="Новая задача..." class="task-input">
                    <button class="btn btn-primary" id="add-task-btn">Добавить</button>
                </div>
                
                <div class="tasks-grid">
                    <div class="task-column">
                        <h2>В работе</h2>
                        <div id="in-progress-list" class="task-list">
                            <p>Загрузка...</p>
                        </div>
                    </div>
                    <div class="task-column">
                        <h2>Завершённые</h2>
                        <div id="completed-list" class="task-list">
                            <p>Загрузка...</p>
                        </div>
                    </div>
                </div>
            </div>
            
            
            <div id="task-modal" class="modal">
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <h2>Редактирование задачи</h2>
                    <div id="task-error" class="error" style="display:none"></div>
                    <form id="task-form">
                        <input type="hidden" id="task-id">
                        <div class="form-group">
                            <label>Заголовок</label>
                            <input type="text" id="task-title" class="task-input">
                        </div>
                        <div class="form-group">
                            <label>Описание</label>
                            <textarea id="task-description" rows="5" class="task-input"></textarea>
                        </div>
                        <div class="form-group">
                            <label>
                                <input type="checkbox" id="task-completed">
                                    Задача выполнена
                            </label>
                        </div>
                        <div class="form-actions">
                            <button type="button" class="btn btn-danger" id="delete-task-btn">Удалить</button>
                        </div>
                    </form>
                </div>
            </div>
        `;

        $('#app').html(html);


        $('#logout-btn').click(() => Auth.logout());
        $('#add-task-btn').click(() => this.addTask());
        $('#new-task-title').keypress(e => {
            if (e.which === 13) this.addTask();
        });


        $('.close').click(() => $('#task-modal').hide());
        $(window).click(e => {
            if ($(e.target).hasClass('modal')) {
                $(e.target).hide();
            }
        });

        $('#task-completed').change(function() {
            const id = $('#task-id').val();
            if ($(this).is(':checked')) {
                Tasks.toggleStatus(id)
                    .then(() => {
                        $('#task-modal').hide();
                        App.loadTasks();
                    });
            }
        });

        $('#delete-task-btn').click(() => {
            const id = $('#task-id').val();
            if (confirm('Удалить задачу?')) {
                Tasks.delete(id)
                    .then(() => {
                        $('#task-modal').hide();
                        this.loadTasks();
                    });
            }
        });


        let saveTimeout;
        $('#task-title, #task-description').on('input', () => {
            clearTimeout(saveTimeout);
            saveTimeout = setTimeout(() => this.saveCurrentTask(), 500);
        });

        this.loadTasks();
    },


    loadTasks() {
        Tasks.loadAll()
            .then(tasks => {
                this.renderTaskList('#in-progress-list', tasks.inProgress, 'in-progress');
                this.renderTaskList('#completed-list', tasks.completed, 'completed');
            })
            .catch(() => {
                $('#in-progress-list').html('<p>Ошибка загрузки</p>');
                $('#completed-list').html('<p>Ошибка загрузки</p>');
            });
    },


    renderTaskList(selector, tasks, status) {
        console.log('Rendering tasks for', selector, tasks);

        if (!tasks || tasks.length === 0) {
            $(selector).html('<p class="empty-list">Нет задач</p>');
            return;
        }

        const html = tasks.map(function(task) {
            return `
            <div class="task-item ${status}" data-id="${task.id}">
                <span class="task-title">${App.escapeHtml(task.title)}</span>
            </div>
        `;
        }).join('');

        $(selector).html(html);

        $(selector).find('.task-item').click(function() {
            const id = $(this).data('id');
            console.log('Clicked task id:', id);
            if (id) {
                Tasks.openTask(id);
            }
        });
    },


    renderTaskModal(task) {
        $('#task-id').val(task.id);
        $('#task-title').val(task.title);
        $('#task-description').val(task.description || '');
        $('#task-completed').prop('checked', task.status === 'COMPLETED');
        $('#task-error').hide();
        $('#task-modal').show();
    },


    addTask() {
        const title = $('#new-task-title').val().trim();
        if (!title) return;

        Tasks.create(title)
            .then(() => {
                $('#new-task-title').val('');
                this.loadTasks();
            });
    },


    saveCurrentTask() {
        const id = $('#task-id').val();
        const title = $('#task-title').val();
        const description = $('#task-description').val();

        if (!id) return;

        Tasks.save(id, title, description)
            .then(() => {
                this.loadTasks();
            })
            .catch(xhr => {
                const msg = xhr.responseJSON?.message || 'Ошибка сохранения';
                $('#task-error').text(msg).show();
            });
    },


    escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    },


    renderLogin() {
        const html = `
            <div class="form-page">
                <h2>Вход</h2>
                <div class="error" id="login-error" style="display:none"></div>
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
                <p class="form-link">
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
                .then(() => this.navigate('home'))
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
            <div class="error" id="register-error" style="display:none"></div>
            <form id="register-form">
                <div class="form-group">
                    <label>Имя пользователя</label>
                    <input type="text" id="register-name" required>
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" id="register-email" required>
                </div>
                <div class="form-group">
                    <label>Пароль</label>
                    <input type="password" id="register-password" required>
                </div>
                <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
            </form>
            <p class="form-link">
                Уже есть аккаунт? <span class="link" id="to-login">Войти</span>
            </p>
        </div>
    `;

        $('#app').html(html);

        $('#register-form').submit(e => {
            e.preventDefault();
            const name = $('#register-name').val();
            const email = $('#register-email').val();
            const password = $('#register-password').val();

            Auth.register(name, email, password)
                .then(() => this.navigate('login'))
                .catch(xhr => {
                    const msg = xhr.responseJSON?.message || 'Ошибка регистрации';
                    $('#register-error').text(msg).show();
                });
        });

        $('#to-login').click(() => this.navigate('login'));
    }
};


$(document).ready(() => App.init());