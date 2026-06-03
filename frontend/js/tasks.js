const Tasks = {
    loadAll() {
        const deferred = $.Deferred();

        $.when(
            API.getTasksInProgress(),
            API.getCompletedTasks()
        ).then(function(inProgress, completed) {

            deferred.resolve({
                inProgress: inProgress[0] || [],
                completed: completed[0] || []
            });
        }).fail(function() {
            deferred.reject();
        });

        return deferred.promise();
    },

    create(title) {
        return API.createTask(title, '');
    },

    openTask(id) {
        API.getTask(id)
            .then(function(task) {
                App.renderTaskModal(task);
            });
    },

    save(id, title, description) {
        return API.updateTask(id, title, description);
    },

    toggleStatus(id) {
        return API.completeTask(id);
    },

    delete(id) {
        return API.deleteTask(id);
    }
};