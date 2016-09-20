package com.rogalabs.lib.model;

import java.io.Serializable

/**
 * Created by roga on 08/07/16.
 */
data class SocialUser(val id: String? , val name: String?, val email: String?, val photoUrl: String? = "", val token: String? = "") : Serializable