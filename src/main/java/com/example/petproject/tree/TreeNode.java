package com.example.petproject.tree;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для создания древовидной структуры
 *
 * @param <T> - объект исходных данных
 * @param <N> - узел дерева
 */
@NoArgsConstructor
@AllArgsConstructor
@Service
@Getter
@Setter
public class TreeNode<T, N extends TreeNode<T, N>> {

    @JsonIgnore
    private TypeAdapter<T,N> typeAdapter;
    private T data;
    @JsonBackReference
    private N parent;
    @JsonManagedReference
    private List<N> children;

    /**
     * Метод для создания древовидной структуры из исходных данных
     * @param initialData - коллекция с исходными данными
     * @param typeAdapter - интерфейс для работы с исходными данными
     * @param <T> - объект исходных данных
     * @param <N> - узел дерева
     * @return корневой узел
     */
    public static <T, N extends TreeNode<T, N>> N makeTree(List<T> initialData,
                                                           TypeAdapter<T, N> typeAdapter) {
        N root = typeAdapter.newInstance();
        root.setChildren(new ArrayList<>());
        /**
         * Создание коллекции, содержащей объекты высшего уровня
         */
        List<T> toplevelNodes = initialData.stream().filter(typeAdapter::isTopLevelObject)
                .collect(Collectors.toList());
        for (T top : toplevelNodes) {
             root.getChildren().add(extractNode(top, root, initialData, typeAdapter));
        }
        return root;
    }

    /**
     * Метод для создания узла из объекта
     * @param object - переданный объект
     * @param parent - родитель object
     * @param initialData - коллекция с исходными данными
     * @param typeAdapter - интерфейс для работы с исходными данными
     * @param <T> - объект исходных данных
     * @param <N> - узел дерева
     * @return узел, содержащий данные об объекте
     */
    public static <T, N extends TreeNode<T, N>> N extractNode(T object, N parent,
                                                              List<T> initialData,
                                                              TypeAdapter<T, N> typeAdapter) {
        N node = typeAdapter.newInstance();
        node.setData(object);
        node.setParent(parent);
        List<T> directChildren = initialData.stream().filter(d -> typeAdapter.isChildOf(object, d))
                .collect(Collectors.toList());
        if (!directChildren.isEmpty()) {
            node.setChildren(new ArrayList<>());
        }
        for (T child: directChildren) {
            node.getChildren().add(extractNode(child, node, initialData, typeAdapter));
        }
        return node;
    }
}
