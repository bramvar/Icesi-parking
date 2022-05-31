package co.edu.icesi.icesiparking.model

import java.io.Serializable

data class Lote(
  var id: String = "",
  var name: String = "",
  var isAvailable: Boolean = false,
  var shortDescription: String = "",
  var completeDescription: String = "",
  var imageID: String = ""
) :Serializable