package com.cultivapp.cultivapp.auth;

/**
 * Role Enumeration for CultivApp (REQ-001: Login Authentication)
 * 
 * Defines the available user roles in the system for Role-Based Access Control (RBAC).
 * This enum is used to assign permissions and determine what features each user can access
 * after successful authentication.
 * 
 * Roles:
 * - ADMIN: System administrator with full access to all features and configurations.
 *   Can manage users, configure system settings, and access all data.
 * 
 * - PRODUCTOR: Agricultural producer/farmer with access to crop management features.
 *   Can manage their own crops, view alerts, and access producer-specific functionality.
 * 
 * Usage: Stored in the User entity and included as a claim in JWT tokens for authorization.
 * 
 * @author CultivApp Team
 * @version 1.0 (REQ-001)
 */
public enum Role { 
    ADMIN,      // Administrator role with full system access
    PRODUCTOR   // Producer role for agricultural users
}
