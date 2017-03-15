package com.rogalabs.lib.model;

import java.io.Serializable

/**
 * Created by roga on 08/07/16.
 */
data class SocialUser(val id: String?, val name: String?, val email: String?,
                      val birthday: String? = "", val photoUrl: String? = "",
                      val hometown: Hometown?, val token: String? = "") : Serializable