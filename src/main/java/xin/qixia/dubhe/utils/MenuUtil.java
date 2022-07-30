//
//package com.wkx.qxiana.utils;
//
//import com.wkx.qxiana.datasource.entity.Menu;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MenuUtil {
//    public static List<Menu> generateMenu(List<Menu> resourceList){
//        List<Menu> menuList=new ArrayList<>();
//        for (Menu childrenMenu:resourceList){
//            //只要不是顶级目录都要寻找其父目录
//            if(childrenMenu.getParentId()!=0){
//                //把其添加到其父目录里
//                for (Menu parentMenu:resourceList){
//                    //找到其父级目录
//                    if(childrenMenu.getParentId()==parentMenu.getId()){
//                        List<Menu> children = parentMenu.getChildren();
//                        if(children==null){
//                            children=new ArrayList<Menu>();
//                        }
//                        children.add(childrenMenu);
//                        parentMenu.setChildren(children);
//                    }
//                }
//            }
//        }
//        //只返回父级菜单即可
//        for (Menu Menu:resourceList){
//            if(Menu.getParentId()==0){
//                menuList.add(Menu);
//            }
//        }
//        return menuList;
//    }
//}
//
