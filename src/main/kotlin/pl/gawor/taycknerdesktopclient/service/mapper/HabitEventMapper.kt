package pl.gawor.taycknerdesktopclient.service.mapper

import pl.gawor.taycknerdesktopclient.model.Habit
import pl.gawor.taycknerdesktopclient.model.HabitEvent
import pl.gawor.taycknerdesktopclient.model.User
import pl.gawor.taycknerdesktopclient.repository.HabitRepository
import pl.gawor.taycknerdesktopclient.repository.entity.HabitEventEntity

class HabitEventMapper : IMapper<HabitEvent, HabitEventEntity> {

    private val habitRepository = HabitRepository()
    private val habitMapper = HabitMapper()

    override fun modelToEntity(model: HabitEvent?): HabitEventEntity {
        println("HabitEventMapper.modelToEntity(model = $model)")
        val result = if (model == null) HabitEventEntity() else {
            HabitEventEntity(model.id, model.date, model.comment, model.count, model.habit.id)
        }
        println("HabitEventMapper.modelToEntity(model = $model) = $result")
        return result
    }

    override fun entityToModel(entity: HabitEventEntity?): HabitEvent? {
        println("HabitEventMapper.entityToModel(model = $entity)")
        val result: HabitEvent? = if (entity == null) null else {
            var habit = habitMapper.entityToModel(habitRepository.read(entity.habitId))
            if (habit == null) {
                habit = Habit()
            }
            HabitEvent(entity.id, entity.date, entity.comment, entity.count, habit)
        }
        println("HabitEventMapper.entityToModel(model = $entity) = $result")
        return result
    }

}