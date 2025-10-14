/**
 * Navigation Bar Component - Top App Bar with User Info (REQ-001: Login Authentication)
 * 
 * This component provides the main navigation bar displayed at the top of the application
 * for authenticated users. It shows branding, notifications, user avatar, and logout functionality.
 * 
 * Component Responsibilities:
 * - Displays CultivApp logo and branding.
 * - Shows notification bell with unread count badge.
 * - Displays user avatar (profile picture).
 * - Provides logout button to end user session.
 * - Shows current user's email for identification.
 * 
 * REQ-001 Integration:
 * - Only displayed after successful login (protected routes).
 * - Uses AuthContext to access user info and logout function.
 * - Logout button clears JWT token and redirects to login.
 * 
 * UI Design:
 * - Green background (#60C37B) matching brand color.
 * - Left: CultivApp logo with plant emoji.
 * - Right: Notifications, user info, logout button.
 * - Responsive layout with flexbox.
 * 
 * @author CultivApp Team
 * @version 1.0 (REQ-001)
 */

import React from 'react'
import { FaBell } from "react-icons/fa6";
import { useAuth } from '../context/AuthContext';

/**
 * NavBar Component - Main navigation bar.
 * 
 * @returns {JSX.Element} Navigation bar with branding and user controls.
 */
export default function NavBar() {
  // Get user info and logout function from authentication context
  const { user, logout } = useAuth();

  return (
    /* 
     * Main Navigation Container
     * Green background (#60C37B) - CultivApp brand color
     * Flexbox layout: logo left, user controls right
     * Horizontal padding (px-4), vertical padding (py-2)
     */
    <nav className="bg-[#60C37B] flex justify-between items-center px-4 py-2">
      
      {/* 
       * LEFT SECTION: Logo and Branding
       * Displays CultivApp logo with plant emoji
       * White background box for logo, brand name in white text
       */}
      <div className="flex items-center gap-2">
        {/* 
         * Logo Container
         * White rounded box containing plant emoji
         * Provides contrast against green navbar background
         */}
        <div className="bg-white rounded-lg p-1">
          <span className="text-green-500 font-bold">🌱</span>
        </div>
        
        {/* 
         * Brand Name
         * "CultivApp" in white text, semibold weight
         * Clearly identifies the application
         */}
        <span className="text-white font-semibold text-lg">CultivApp</span>
      </div>

      {/* 
       * RIGHT SECTION: User Controls
       * Contains notifications, user info, and logout button
       * Flexbox with gap for consistent spacing
       */}
      <div className="flex items-center gap-4">
        
        {/* 
         * Notification Bell Icon
         * Shows notification count badge (hardcoded "10" for demo)
         * Future: Replace with actual notification count from backend
         * Relative positioning for badge overlay
         */}
        <div className="relative">
          {/* Bell icon from react-icons library */}
          <FaBell className='text-white' />
          
          {/* 
           * Notification Badge
           * Red circle with white text showing unread count
           * Positioned absolutely in top-right of bell icon
           * Future: Conditionally render only if count > 0
           */}
          <span className="absolute -top-2 -right-2 bg-red-500 text-white text-xs font-bold rounded-full px-1.5">
            10
          </span>
        </div>

        {/* 
         * User Email Display
         * Shows the currently logged-in user's email
         * Uses auth context to get user info
         * Only visible if user is authenticated
         * Provides context for who is logged in
         */}
        {user && (
          <span className="text-white text-sm">
            {user.email}
          </span>
        )}

        {/* 
         * User Avatar
         * Profile picture from placeholder service (pravatar.cc)
         * Circular with white border for visual separation
         * Future: Replace with actual user profile picture
         * Size: 32x32 pixels (w-8 h-8)
         */}
        <img
          src="https://i.pravatar.cc/40"
          alt="user avatar"
          className="w-8 h-8 rounded-full border-2 border-white"
        />

        {/* 
         * Logout Button
         * Allows user to end their session
         * Calls logout function from AuthContext
         * White text on transparent background
         * Hover effect: Light white background overlay
         * Calls logout which:
         * 1. Removes JWT token from localStorage
         * 2. Clears user state in AuthContext
         * 3. Redirects to /login page
         */}
        <button
          onClick={logout}
          className="text-white text-sm px-3 py-1 rounded hover:bg-white hover:bg-opacity-20 transition"
          aria-label="Cerrar sesión" // Accessibility: Screen reader label
        >
          Cerrar Sesión
        </button>
      </div>
    </nav>
  )
}
