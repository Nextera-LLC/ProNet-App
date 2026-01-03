import { Injectable } from '@angular/core';
@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly TOKEN_KEY = 'JwtToken';

  isAuthenticated(): boolean {
    const token = localStorage.getItem(this.TOKEN_KEY);
    if (!token) return false;

    const exp = this.getTokenExp(token);
    if (!exp) {
      this.logout();
      return false;
    }

    const now = Math.floor(Date.now() / 1000);
    if (exp <= now) {
      this.logout(); // REMOVE expired token
      return false;
    }

    return true;
  }

  logout() {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  private getTokenExp(token: string): number | null {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.exp ?? null;
    } catch {
      return null;
    }
  }
}
