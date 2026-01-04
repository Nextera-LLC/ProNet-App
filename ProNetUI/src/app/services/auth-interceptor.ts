// services/auth-interceptor.ts
import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

    // don't attach token to auth endpoints
    if (req.url.includes('/auth/')) {
      return next(req);
    }
    
  const token = localStorage.getItem('JwtToken'); // keep key consistent
  return next(
    token ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } }) : req
  );
};
