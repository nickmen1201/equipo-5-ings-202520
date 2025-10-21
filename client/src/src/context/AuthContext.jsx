

import React, { createContext, useContext, useState, useEffect } from 'react';
import { loginUser as loginUserService, logoutUser as logoutUserService, getToken } from '../services/authService';


const AuthContext = createContext(null);


export function AuthProvider({ children }) {
  
  const [user, setUser] = useState(null);
  
  
  const [loading, setLoading] = useState(true);

  
  useEffect(() => {
    
    const token = getToken();
    
    if (token) {
      // Token exists - user was previously logged in
      // Restore user info from localStorage
      const storedUser = localStorage.getItem('user');
      
      if (storedUser) {
        // Parse and set user state to restore authentication
        setUser(JSON.parse(storedUser));
      }
    }
    
    // Mark initialization as complete
    setLoading(false);
  }, []); // Empty dependency array = run once on mount

  
  const login = async (email, password) => {
    // Call authentication service to validate credentials with backend
    const data = await loginUserService(email, password);
    
    // Create user object from backend response
    const userData = {
      id: data.userId, // User ID from backend
      email: data.email, // Email from backend
      role: data.role // ADMIN or PRODUCTOR
    };
    
    // Update state - triggers re-render of components using AuthContext
    setUser(userData);
    
    // Persist user info in localStorage for auto-login on page reload
    localStorage.setItem('user', JSON.stringify(userData));
    
    // Return user data to caller (Login component) for routing
    return userData;
  };


  const logout = () => {
    // Remove JWT token from localStorage
    logoutUserService();
    
    // Remove user info from localStorage
    localStorage.removeItem('user');
    
    // Clear user state - triggers re-render as logged out
    setUser(null);
    
    // Redirect to login page
    window.location.href = '/login';
  };

  
  const value = {
    user,      // Current user or null
    login,     // Login function
    logout,    // Logout function
    loading    // Loading state
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  // Get context value
  const context = useContext(AuthContext)
  
  // Ensure component is wrapped by AuthProvider
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  
  return context;
}