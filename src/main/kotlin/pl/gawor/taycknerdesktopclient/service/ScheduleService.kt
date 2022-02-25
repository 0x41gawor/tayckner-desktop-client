package pl.gawor.taycknerdesktopclient.service

import pl.gawor.taycknerdesktopclient.model.Schedule
import pl.gawor.taycknerdesktopclient.repository.ScheduleRepository
import pl.gawor.taycknerdesktopclient.repository.entity.ScheduleEntity
import pl.gawor.taycknerdesktopclient.service.mapper.ScheduleMapper
import java.time.LocalDate

class ScheduleService(repository: ScheduleRepository, mapper: ScheduleMapper) :
    Service<Schedule, ScheduleEntity>(repository, mapper) {

    fun list(date: LocalDate): List<Schedule>? {
        println("ScheduleService.list(date = $date)")
        val entities = (repository as ScheduleRepository).list(date)
        val models = ArrayList<Schedule>()
        if (entities != null) {
            for (entity in entities) {
                val model = mapper.entityToModel(entity)
                models.add(model!!)
            }
            println("ScheduleService.list(date = $date) = $models")
            return models
        }
        println("ScheduleService.list(date = $date) = null")
        return null
    }
}