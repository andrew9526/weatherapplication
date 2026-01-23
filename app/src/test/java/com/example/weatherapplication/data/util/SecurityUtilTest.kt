package com.example.weatherapplication.data.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Unit tests for SecurityUtil
 */
class SecurityUtilTest {
    
    @Test
    fun `hashPassword returns non-empty string`() {
        val hashedPassword = SecurityUtil.hashPassword("password123")

        assertThat(hashedPassword).isNotEmpty()
    }
    
    @Test
    fun `hashPassword returns string with salt and hash separated by colon`() {
        val hashedPassword = SecurityUtil.hashPassword("password123")
        assertThat(hashedPassword).contains(":")
        val parts = hashedPassword.split(":")
        assertThat(parts).hasSize(2)
        assertThat(parts[0]).isNotEmpty() // salt
        assertThat(parts[1]).isNotEmpty() // hash
    }
    
    @Test
    fun `hashPassword produces different hashes for same password`() {
        val hash1 = SecurityUtil.hashPassword("password123")
        val hash2 = SecurityUtil.hashPassword("password123")

        assertThat(hash1).isNotEqualTo(hash2)
    }
    
    @Test
    fun `verifyPassword returns true for correct password`() {
        val password = "password123"
        val hashedPassword = SecurityUtil.hashPassword(password)

        val isValid = SecurityUtil.verifyPassword(password, hashedPassword)

        assertThat(isValid).isTrue()
    }
    
    @Test
    fun `verifyPassword returns false for incorrect password`() {
        val password = "password123"
        val wrongPassword = "wrongpassword"
        val hashedPassword = SecurityUtil.hashPassword(password)

        val isValid = SecurityUtil.verifyPassword(wrongPassword, hashedPassword)

        assertThat(isValid).isFalse()
    }
    
    @Test
    fun `verifyPassword returns false for invalid hash format`() {
        val password = "password123"
        val invalidHash = "not_a_valid_hash"

        val isValid = SecurityUtil.verifyPassword(password, invalidHash)

        assertThat(isValid).isFalse()
    }
    
    @Test
    fun `verifyPassword works correctly with special characters`() {
        val password = "p@ssw0rd!#$%"
        val hashedPassword = SecurityUtil.hashPassword(password)

        val isValid = SecurityUtil.verifyPassword(password, hashedPassword)

        assertThat(isValid).isTrue()
    }
}
