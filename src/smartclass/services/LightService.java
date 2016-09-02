/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartclass.services;

import context.arch.comm.DataObject;
import context.arch.service.Service;
import context.arch.service.helper.FunctionDescription;
import context.arch.service.helper.FunctionDescriptions;
import context.arch.service.helper.ServiceInput;
import context.arch.widget.Widget;
import smartclass.ui.ClassRoomUI;

/**
 *
 * @author Pedro
 */
public class LightService extends Service {

    public LightService(final Widget widget) {
        super(widget, "LightService",
                new FunctionDescriptions() {
            {
                add(new FunctionDescription(
                        "lightControl",
                        "Sets the light on or off",
                        widget.getNonConstantAttributes()));
            }
        });
    }

    @Override
    public DataObject execute(ServiceInput si) {
        int light = si.getInput().getAttributeValue("light");
        if(light == 1){
            ClassRoomUI.getInstance().lightOn();
        }else{
            ClassRoomUI.getInstance().lightOff();
        }
        return new DataObject(); // no particular info to return
    }

}
