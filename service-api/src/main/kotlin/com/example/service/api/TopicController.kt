package com.example.service.api

import com.example.service.api.service.NotFoundException
import com.example.service.api.service.ServiceException
import com.example.service.api.service.TopicService
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/v1/topics")
class TopicController(
    private val topicService: TopicService
) {
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createTopic(@RequestHeader("x-tenant") tenant: String, @RequestBody topic: Topic) {
        try {
            topicService.createTopic(tenant, topic)
        } catch (e: RuntimeException) {
            throw handle(e)
        }
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun updateTopic(@RequestHeader("x-tenant") tenant: String, @RequestBody topic: Topic) {
        try {
            topicService.updateTopic(tenant, topic)
        } catch (e: RuntimeException) {
            throw handle(e)
        }
    }

    @GetMapping("/{topicName}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getTopic(
        @RequestHeader("x-tenant") tenant: String,
        @RequestParam("topicName") topicName: String
    ): Topic {
        try {
            return topicService.getTopic(tenant, topicName)
        } catch (e: RuntimeException) {
            throw handle(e)
        }
    }

    @DeleteMapping("/{topicName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteTopic(@RequestHeader("x-tenant") tenant: String, @RequestParam("topicName") topicName: String) {
        try {
            topicService.deleteTopic(tenant, topicName)
        } catch (e: RuntimeException) {
            throw handle(e)
        }
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun getTopics(@RequestHeader("x-tenant") tenant: String): Flow<Topic> {
        try {
            return topicService.getTopics(tenant)
        } catch (e: RuntimeException) {
            throw handle(e)
        }
    }

    fun handle(e: RuntimeException): ResponseStatusException {
        if (e is NotFoundException) {
            return ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        } else if (e is ServiceException) {
            return ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.message)
        }
        return ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
    }
}
