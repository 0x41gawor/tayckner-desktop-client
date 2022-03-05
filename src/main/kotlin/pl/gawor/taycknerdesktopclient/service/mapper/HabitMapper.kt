package pl.gawor.taycknerdesktopclient.service.mapper

import pl.gawor.taycknerdesktopclient.model.Habit
import pl.gawor.taycknerdesktopclient.model.User
import pl.gawor.taycknerdesktopclient.repository.UserRepository
import pl.gawor.taycknerdesktopclient.repository.entity.HabitEntity

class HabitMapper : IMapper<Habit, HabitEntity> {

    private val userRepository = UserRepository()
    private val userMapper = UserMapper()

    override fun modelToEntity(model: Habit?): HabitEntity {
        println("HabitMapper.modelToEntity(model = $model)")
        val result = if (model == null) HabitEntity() else {
            HabitEntity(model.id, model.name, model.description, model.color, model.user.id)
        }
        println("HabitMapper.modelToEntity(model = $model) = $result")
        return result
    }

    override fun entityToModel(entity: HabitEntity?): Habit? {
        println("HabitMapper.entityToModel(model = $entity)")
        val result: Habit? = if (entity == null) null else {
            var user = userMapper.entityToModel(userRepository.read(entity.userId))
            if (user == null) {
                user = User()
            }
            Habit(entity.id, entity.name, entity.description, entity.color, user)
        }
        println("HabitMapper.entityToModel(model = $entity) = $result")
        return result
    }
}