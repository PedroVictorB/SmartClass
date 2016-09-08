/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartclass.enactor;

import context.arch.discoverer.ComponentDescription;
import context.arch.discoverer.component.NonConstantAttributeElement;
import context.arch.discoverer.query.AbstractQueryItem;
import context.arch.discoverer.query.ElseQueryItem;
import context.arch.discoverer.query.ORQueryItem;
import context.arch.discoverer.query.RuleQueryItem;
import context.arch.discoverer.query.comparison.AttributeComparison;
import context.arch.enactor.Enactor;
import context.arch.enactor.EnactorReference;
import context.arch.service.helper.ServiceInput;
import context.arch.storage.AttributeNameValue;
import context.arch.storage.Attributes;
import context.arch.widget.Widget;
import context.arch.widget.Widget.WidgetData;

/**
 *
 * @author Pedro
 */
public class RoomEnactor extends Enactor {

    public RoomEnactor(AbstractQueryItem<?, ?> inWidgetSubscriptionQuery, AbstractQueryItem<?, ?> outWidgetSubscriptionQuery, String outcomeName, String shortId, String type) {
        super(inWidgetSubscriptionQuery, outWidgetSubscriptionQuery, outcomeName, shortId);

        if ("LightWidget".equals(type)) {
            //Cria uma query
            AbstractQueryItem<?, ?> offQI
                    = new ORQueryItem(
                            RuleQueryItem.instance(
                                    new NonConstantAttributeElement(AttributeNameValue.instance("presence", 0)),
                                    new AttributeComparison(AttributeComparison.Comparison.EQUAL)),
                            RuleQueryItem.instance(
                                    new NonConstantAttributeElement(AttributeNameValue.instance("brightness", 50)),
                                    new AttributeComparison(AttributeComparison.Comparison.GREATER_EQUAL))
                    );

            //cria a referencia
            EnactorReference er = new RoomEnactorLightReference(
                    offQI,
                    "LightOff");

            //adiciona o serviço
            er.addServiceInput(new ServiceInput("LightService", "lightControl",
                    new Attributes() {
                {
                    addAttribute("light", Integer.class);
                }
            }));

            //adiciona o enactor
            addReference(er);

            //Cria uma query
            er = new RoomEnactorLightReference(
                    new ElseQueryItem(offQI),
                    "LightOn");

            //adiciona o serviço
            er.addServiceInput(new ServiceInput("LightService", "lightControl",
                    new Attributes() {
                {
                    addAttribute("light", Integer.class);
                }
            }));

            //adiciona o enactor
            addReference(er);
        } else if ("ProjectorWidget".equals(type)) {
            //Cria uma query
            AbstractQueryItem<?, ?> offProjector
                    = new ORQueryItem(
                            RuleQueryItem.instance(
                                    new NonConstantAttributeElement(AttributeNameValue.instance("presence", 0)),
                                    new AttributeComparison(AttributeComparison.Comparison.EQUAL))
                    );

            //cria a referencia
            EnactorReference er = new RoomEnactorProjectorReference(
                    offProjector,
                    "ProjectorOff");

            //adiciona o serviço
            er.addServiceInput(new ServiceInput("ProjectorService", "projectorControl",
                    new Attributes() {
                {
                    addAttribute("status", Integer.class);
                }
            }));

            //adiciona o enactor
            addReference(er);

            //Cria uma query
            er = new RoomEnactorProjectorReference(
                    new ElseQueryItem(offProjector),
                    "ProjectorOn");

            //adiciona o serviço
            er.addServiceInput(new ServiceInput("ProjectorService", "projectorControl",
                    new Attributes() {
                {
                    addAttribute("status", Integer.class);
                }
            }));

            //adiciona o enactor
            addReference(er);
        } else if ("ComputerWidget".equals(type)) {
            //Cria uma query
            AbstractQueryItem<?, ?> offProjector
                    = new ORQueryItem(
                            RuleQueryItem.instance(
                                    new NonConstantAttributeElement(AttributeNameValue.instance("presence", 0)),
                                    new AttributeComparison(AttributeComparison.Comparison.EQUAL))
                    );

            //cria a referencia
            EnactorReference er = new RoomEnactorComputerReference(
                    offProjector,
                    "ComputerOff");

            //adiciona o serviço
            er.addServiceInput(new ServiceInput("ComputerService", "computerControl",
                    new Attributes() {
                {
                    addAttribute("status", Integer.class);
                    addAttribute("time", Integer.class);
                }
            }));

            //adiciona o enactor
            addReference(er);

            //Cria uma query
            er = new RoomEnactorComputerReference(
                    new ElseQueryItem(offProjector),
                    "ComputerOn");

            //adiciona o serviço
            er.addServiceInput(new ServiceInput("ComputerService", "computerControl",
                    new Attributes() {
                {
                    addAttribute("status", Integer.class);
                    addAttribute("time", Integer.class);
                }
            }));

            //adiciona o enactor
            addReference(er);
        } else if ("AirWidget".equals(type)) {
            //Cria uma query
            AbstractQueryItem<?, ?> offProjector
                    = new ORQueryItem(
                            RuleQueryItem.instance(
                                    new NonConstantAttributeElement(AttributeNameValue.instance("presence", 0)),
                                    new AttributeComparison(AttributeComparison.Comparison.EQUAL))
                    );

            //cria a referencia
            EnactorReference er = new RoomEnactorAirReference(
                    offProjector,
                    "AirOff");

            //adiciona o serviço
            er.addServiceInput(new ServiceInput("AirService", "airControl",
                    new Attributes() {
                {
                    addAttribute("status", Integer.class);
                    addAttribute("temperature", Integer.class);
                    addAttribute("time", Integer.class);
                }
            }));

            //adiciona o enactor
            addReference(er);

            //Cria uma query
            er = new RoomEnactorAirReference(
                    new ElseQueryItem(offProjector),
                    "AirOn");

            //adiciona o serviço
            er.addServiceInput(new ServiceInput("AirService", "airControl",
                    new Attributes() {
                {
                    addAttribute("status", Integer.class);
                    addAttribute("temperature", Integer.class);
                    addAttribute("time", Integer.class);
                }
            }));

            //adiciona o enactor
            addReference(er);
        }else if("ProfessorWidget".equals(type)){
            //Cria uma query
            AbstractQueryItem<?, ?> offProjector
                    = new ORQueryItem(
                            
                    );

            //cria a referencia
            EnactorReference er = new RoomEnactorProfessorReference(
                    offProjector,
                    "ProfessorChange");

            //adiciona o serviço
            er.addServiceInput(new ServiceInput("ProfessorService", "professorControl",
                    new Attributes() {
                {
                    addAttribute("temperature", Integer.class);
                    addAttribute("time", Integer.class);
                    addAttribute("slides", Integer.class);
                }
            }));

            //adiciona o enactor
            addReference(er);
        }

        start();

    }

    private class RoomEnactorLightReference extends EnactorReference {

        public RoomEnactorLightReference(AbstractQueryItem<?, ?> conditionQuery, String outcomeValue) {
            super(RoomEnactor.this, conditionQuery, outcomeValue);
        }

        @Override
        protected Attributes conditionSatisfied(ComponentDescription inWidgetState, Attributes outAtts) {
            long timestamp = outAtts.getAttributeValue(Widget.TIMESTAMP);
            WidgetData data = new WidgetData("LightWidget", timestamp);
            int light;
            if ("LightOn".equals(outcomeValue)) {
                short brightness = inWidgetState.getAttributeValue("brightness");
                if(brightness >= 50){
                    light = 0;
                }else{
                    light = 1;
                }
            } else {
                light = 0;
            }

            data.setAttributeValue("light", light);
            outAtts.putAll(data.toAttributes());

            return outAtts;
        }

    }

    private class RoomEnactorProjectorReference extends EnactorReference {

        public RoomEnactorProjectorReference(AbstractQueryItem<?, ?> conditionQuery, String outcomeValue) {
            super(RoomEnactor.this, conditionQuery, outcomeValue);
        }

        @Override
        protected Attributes conditionSatisfied(ComponentDescription inWidgetState, Attributes outAtts) {
            long timestamp = outAtts.getAttributeValue(Widget.TIMESTAMP);
            WidgetData data = new WidgetData("ProjectorWidget", timestamp);
            int status;
            if ("ProjectorOn".equals(outcomeValue)) {
                status = 1;
            } else {
                status = 0;
            }

            data.setAttributeValue("status", status);
            outAtts.putAll(data.toAttributes());

            return outAtts;
        }

    }
    
    private class RoomEnactorComputerReference extends EnactorReference {

        public RoomEnactorComputerReference(AbstractQueryItem<?, ?> conditionQuery, String outcomeValue) {
            super(RoomEnactor.this, conditionQuery, outcomeValue);
        }

        @Override
        protected Attributes conditionSatisfied(ComponentDescription inWidgetState, Attributes outAtts) {
            long timestamp = outAtts.getAttributeValue(Widget.TIMESTAMP);
            WidgetData data = new WidgetData("ComputerWidget", timestamp);
            int status;
            if ("ComputerOn".equals(outcomeValue)) {
                status = 1;
            } else {
                status = 0;
            }

            data.setAttributeValue("status", status);
            data.setAttributeValue("time", inWidgetState.getAttributeValue("time"));
            outAtts.putAll(data.toAttributes());

            return outAtts;
        }

    }
    
    private class RoomEnactorAirReference extends EnactorReference {

        public RoomEnactorAirReference(AbstractQueryItem<?, ?> conditionQuery, String outcomeValue) {
            super(RoomEnactor.this, conditionQuery, outcomeValue);
        }

        @Override
        protected Attributes conditionSatisfied(ComponentDescription inWidgetState, Attributes outAtts) {
            long timestamp = outAtts.getAttributeValue(Widget.TIMESTAMP);
            WidgetData data = new WidgetData("AirWidget", timestamp);
            int status;
            if ("AirOn".equals(outcomeValue)) {
                status = 1;
            } else {
                status = 0;
            }

            data.setAttributeValue("status", status);
            data.setAttributeValue("temperature", inWidgetState.getAttributeValue("temperature"));
            data.setAttributeValue("time", inWidgetState.getAttributeValue("time"));
            outAtts.putAll(data.toAttributes());

            return outAtts;
        }

    }
    
    private class RoomEnactorProfessorReference extends EnactorReference {

        public RoomEnactorProfessorReference(AbstractQueryItem<?, ?> conditionQuery, String outcomeValue) {
            super(RoomEnactor.this, conditionQuery, outcomeValue);
        }

        @Override
        protected Attributes conditionSatisfied(ComponentDescription inWidgetState, Attributes outAtts) {
            long timestamp = outAtts.getAttributeValue(Widget.TIMESTAMP);
            WidgetData data = new WidgetData("ProfessorWidget", timestamp);

            data.setAttributeValue("slides", inWidgetState.getAttributeValue("slides"));
            data.setAttributeValue("temperature", inWidgetState.getAttributeValue("temperature"));
            data.setAttributeValue("time", inWidgetState.getAttributeValue("time"));
            outAtts.putAll(data.toAttributes());

            return outAtts;
        }

    }
}
