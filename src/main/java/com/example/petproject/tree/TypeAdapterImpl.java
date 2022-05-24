package com.example.petproject.tree;

import com.example.petproject.model.Comment;
import org.springframework.stereotype.Service;

@Service
public class TypeAdapterImpl implements TypeAdapter<Comment,CommentNode>{

    @Override
    public CommentNode newInstance() {
        return new CommentNode();
    }

    @Override
    public boolean isChildOf(Comment parentNodeData, Comment childNodeData) {
        return parentNodeData.getId() == childNodeData.getParentId();
    }

    @Override
    public boolean isTopLevelObject(Comment comment) {
        return comment.getParentId()==0;
    }
}
