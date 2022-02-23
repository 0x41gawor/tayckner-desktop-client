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
        if (model == null) return ScheduleEntity()
        return ScheduleEntity(model.id, model.name, model.startTime, model.endTime, model.duration, model.user.id)
    }

    override fun entityToModel(entity: ScheduleEntity?): Schedule? {
        if (entity == null) return null
        var user = userService.read(entity.userId)
        if (user == null) {
            user = User()
        }
        return Schedule(entity.id, entity.name, entity.startTime, entity.endTime, entity.duration, user)
    }
}