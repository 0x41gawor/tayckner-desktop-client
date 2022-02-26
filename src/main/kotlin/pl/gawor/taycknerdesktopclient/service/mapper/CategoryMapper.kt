package pl.gawor.taycknerdesktopclient.service.mapper

import pl.gawor.taycknerdesktopclient.model.Category
import pl.gawor.taycknerdesktopclient.model.User
import pl.gawor.taycknerdesktopclient.repository.UserRepository
import pl.gawor.taycknerdesktopclient.repository.entity.CategoryEntity

class CategoryMapper : IMapper<Category, CategoryEntity> {

    private val userRepository = UserRepository()
    private val userMapper = UserMapper()

    override fun modelToEntity(model: Category?): CategoryEntity {
        println("CategoryMapper.modelToEntity(model = $model)")
        val result = if (model == null) CategoryEntity() else {
            CategoryEntity(model.id, model.name, model.description, model.color, model.user.id)
        }
        println("CategoryMapper.modelToEntity(model = $model) = $result")
        return result
    }

    override fun entityToModel(entity: CategoryEntity?): Category? {
        println("CategoryMapper.entityToModel(model = $entity)")
        val result: Category? = if (entity == null) null else {
            var user = userMapper.entityToModel(userRepository.read(entity.userId))
            if (user == null) {
                user = User()
            }
            Category(entity.id, entity.name, entity.description, entity.color, user)
        }
        println("CategoryMapper.entityToModel(model = $entity) = $result")
        return result
    }
}