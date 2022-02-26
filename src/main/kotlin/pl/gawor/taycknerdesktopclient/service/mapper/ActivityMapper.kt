package pl.gawor.taycknerdesktopclient.service.mapper

import pl.gawor.taycknerdesktopclient.model.Activity
import pl.gawor.taycknerdesktopclient.model.Category
import pl.gawor.taycknerdesktopclient.repository.CategoryRepository
import pl.gawor.taycknerdesktopclient.repository.entity.ActivityEntity

class ActivityMapper :IMapper<Activity, ActivityEntity> {

    private val categoryRepository = CategoryRepository()
    private val categoryMapper = CategoryMapper()

    override fun modelToEntity(model: Activity?): ActivityEntity {
        println("ActivityMapper.modelToEntity(model = $model)")
        val result: ActivityEntity = if (model == null) ActivityEntity() else {
            ActivityEntity(model.id, model.name, model.startTime, model.endTime, model.date, model.duration, model.breaks, model.category.id)
        }
        println("ActivityMapper.modelToEntity(model = $model) = $result")
        return result
    }

    override fun entityToModel(entity: ActivityEntity?): Activity? {
        println("ScheduleMapper.entityToModel(model = $entity)")
        val result: Activity? = if (entity == null) null else {
            var category = categoryMapper.entityToModel(categoryRepository.read(entity.categoryId))
            if (category == null) {
                category = Category()
            }
            Activity(entity.id, entity.name, entity.startTime, entity.endTime, entity.date, entity.duration, entity.breaks, category)
        }
        println("ScheduleMapper.entityToModel(model = $entity) = $result")
        return result
    }
}