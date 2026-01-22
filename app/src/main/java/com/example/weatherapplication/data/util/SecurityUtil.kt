package com.example.weatherapplication.data.util

import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

/**
 * Security utility for password hashing
 * Uses SHA-256 with salt
 */
object SecurityUtil {
    
    /**
     * Hash a password with salt
     * 
     * @param password Plain text password
     * @return Hashed password with salt (format: "salt:hash")
     */
    fun hashPassword(password: String): String {
        val salt = generateSalt()
        val hash = hashWithSalt(password, salt)
        return "$salt:$hash"
    }
    
    /**
     * Verify password against stored hash
     * 
     * @param password Plain text password to verify
     * @param storedHash Stored hash (format: "salt:hash")
     * @return true if password matches
     */
    fun verifyPassword(password: String, storedHash: String): Boolean {
        val parts = storedHash.split(":")
        if (parts.size != 2) return false
        
        val salt = parts[0]
        val hash = parts[1]
        
        val inputHash = hashWithSalt(password, salt)
        return hash == inputHash
    }
    
    /**
     * Generate random salt
     */
    private fun generateSalt(): String {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return Base64.getEncoder().encodeToString(salt)
    }
    
    /**
     * Hash password with salt using SHA-256
     */
    private fun hashWithSalt(password: String, salt: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val saltedPassword = "$salt$password"
        val bytes = md.digest(saltedPassword.toByteArray())
        return Base64.getEncoder().encodeToString(bytes)
    }
}
