FROM nginx:alpine
RUN rm -rf /usr/share/nginx/html/*

# ✅ Copy browser build output directly to nginx root
COPY dist/ProNetUI/browser/ /usr/share/nginx/html/

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
