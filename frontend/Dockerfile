FROM nginx:alpine


COPY index.html /usr/share/nginx/html/index.html
COPY css/style.css /usr/share/nginx/html/css/style.css
COPY js/api.js /usr/share/nginx/html/js/api.js
COPY js/auth.js /usr/share/nginx/html/js/auth.js
COPY js/tasks.js /usr/share/nginx/html/js/tasks.js
COPY js/app.js /usr/share/nginx/html/js/app.js


COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80