package com.example.petproject.tree;

import org.springframework.stereotype.Service;

/**
 * Интерфейс для обработки исходных данных, преобразуемых в дерево
 */
@Service
public interface TypeAdapter<T,N> {
    /**
     * Метод для создания нового узла дерева
     * @return новый узел
     */
    N newInstance();

    /**
     * Метод для определения потомка заданного объекта
     * @param parentNodeData - узел, для которого определяется потомок
     * @param childNodeData - узел, для которого определяется является ли он потомком parentNode
     * @return являются ли заданные узлы родителем и потомком
     */
    boolean isChildOf(T parentNodeData, T childNodeData);

    /**
     * Метод для определения объекта вычсшего уровня
     * @return находится ли объект на вершине иерархии дерева
     */
    boolean isTopLevelObject(T object);
}
