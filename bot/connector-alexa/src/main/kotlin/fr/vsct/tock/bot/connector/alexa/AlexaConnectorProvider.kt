/*
 * Copyright (C) 2017 VSCT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.vsct.tock.bot.connector.alexa

import fr.vsct.tock.bot.connector.Connector
import fr.vsct.tock.bot.connector.ConnectorConfiguration
import fr.vsct.tock.bot.connector.ConnectorProvider
import fr.vsct.tock.bot.connector.ConnectorType
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * [ConnectorProvider] for [AlexaConnector].
 */
internal object AlexaConnectorProvider : ConnectorProvider {

    private const val PROJECT_IDS = "_project_ids"
    private const val PROJECT_TIMESTAMP = "_project_timestamp"
    private const val PROJECT_ID_SEPARATOR = ","
    private const val ALEXA_MAPPER = "_mapper"

    private const val DEFAULT_TIMESTAMP: Long = 10000L

    override val connectorType: ConnectorType get() = alexaConnectorType

    override fun connector(connectorConfiguration: ConnectorConfiguration): Connector =
        with(connectorConfiguration) {
            AlexaConnector(
                connectorId,
                path,
                Class.forName(parameters[ALEXA_MAPPER]).kotlin.primaryConstructor!!.call(connectorId) as AlexaTockMapper,
                parameters[PROJECT_IDS]
                    ?.split(PROJECT_ID_SEPARATOR)
                    ?.filter { it.isNotBlank() }
                    ?.toSet()
                        ?: emptySet(),
                parameters[PROJECT_TIMESTAMP]?.toLong() ?: DEFAULT_TIMESTAMP
            )
        }

    /**
     * Create a new messenger connector configuration.
     */
    fun newConfiguration(
        applicationId: String,
        path: String,
        applicationName: String,
        alexaTockMapper: KClass<out AlexaTockMapper>,
        allowedProjectIds: Set<String> = emptySet(),
        timestampMs: Long = DEFAULT_TIMESTAMP
    ): ConnectorConfiguration {

        return ConnectorConfiguration(
            applicationId,
            path,
            connectorType,
            applicationName,
            null,
            parameters =
            mapOf(
                PROJECT_IDS to allowedProjectIds.joinToString(PROJECT_ID_SEPARATOR),
                ALEXA_MAPPER to alexaTockMapper.qualifiedName!!,
                PROJECT_TIMESTAMP to timestampMs.toString()
            )
        )
    }
}

internal class AlexaConnectorProviderService : ConnectorProvider by AlexaConnectorProvider