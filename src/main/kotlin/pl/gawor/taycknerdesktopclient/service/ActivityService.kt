package pl.gawor.taycknerdesktopclient.service

import pl.gawor.taycknerdesktopclient.model.Activity
import pl.gawor.taycknerdesktopclient.repository.ActivityRepository
import pl.gawor.taycknerdesktopclient.repository.entity.ActivityEntity
import pl.gawor.taycknerdesktopclient.service.mapper.ActivityMapper
import java.time.LocalDate

class ActivityService(repository: ActivityRepository, mapper: ActivityMapper) :
    Service<Activity, ActivityEntity>(repository, mapper) {

    fun list(date: LocalDate): List<Activity>? {
        println("ActivityService.list(date = $date)")
        val entities = (repository as ActivityRepository).list(date)
        val models = ArrayList<Activity>()
        if (entities != null) {
            for (entity in entities) {
                val model = mapper.entityToModel(entity)
                models.add(model!!)
            }
            println("ActivityService.list(date = $date) = $models")
            return models
        }
        println("ActivityService.list(date = $date) = null")
        return null
    }
}