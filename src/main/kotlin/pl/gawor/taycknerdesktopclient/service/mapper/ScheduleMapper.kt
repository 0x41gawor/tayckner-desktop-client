package pl.gawor.taycknerdesktopclient.service.mapper

import pl.gawor.taycknerdesktopclient.model.Schedule
import pl.gawor.taycknerdesktopclient.model.User
import pl.gawor.taycknerdesktopclient.repository.UserRepository
import pl.gawor.taycknerdesktopclient.repository.entity.ScheduleEntity
import pl.gawor.taycknerdesktopclient.repository.entity.UserEntity
import pl.gawor.taycknerdesktopclient.service.Service


class ScheduleMapper : IMapper<Schedule, ScheduleEntity> {

    private val userRepository = UserRepository()
    private val userMapper = UserMapper()
    private val userService = Service<User, UserEntity>(userRepository, userMapper)

    override fun modelToEntity(model: Schedule?): ScheduleEntity {
        println("ScheduleMapper.modelToEntity(model = $model)")
        val result: ScheduleEntity = if (model == null) ScheduleEntity() else ScheduleEntity(model.id, model.name, model.startTime, model.endTime, model.duration, model.user.id)
        println("ScheduleMapper.modelToEntity(model = $model) = $result")
        return result
    }

    override fun entityToModel(entity: ScheduleEntity?): Schedule? {
        println("ScheduleMapper.entityToModel(model = $entity)")
        val result: Schedule? = if (entity == null) null else {
            var user = userService.read(entity.userId)
            if (user == null) {
                user = User()
            }
            Schedule(entity.id, entity.name, entity.startTime, entity.endTime, entity.duration, user)
        }
        println("ScheduleMapper.entityToModel(model = $entity) = $result")
        return result
    }
}