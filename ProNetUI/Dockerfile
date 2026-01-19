FROM nginx:alpine
RUN rm -rf /usr/share/nginx/html/*

# Copy the Angular output folder into nginx
# IMPORTANT: Replace <appName> with your real dist folder name
COPY dist/ProNetUI/ /usr/share/nginx/html/

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
