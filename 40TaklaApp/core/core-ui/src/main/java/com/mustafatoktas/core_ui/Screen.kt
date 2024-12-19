package com.mustafatoktas.core_ui

import kotlinx.serialization.Serializable

sealed class Graph {

    @Serializable
    data object MainGraph : Graph()
}

sealed class Destination {

    @Serializable
    data object MainScreen : Destination()

    @Serializable
    data object CongratulationScreen : Destination()

    @Serializable
    data object ListScreen : Destination()

    @Serializable
    data object AboutScreen : Destination()
}
