<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns="http://repast.sf.net/statecharts" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.2/notation">
  <StateMachine xmi:id="_NQ9TUZpBEeqqQIEemyx-2w" agentType="entities.Drone" package="entities.chart" className="C2A" nextID="20" id="C2A" uuid="_NQ9TUJpBEeqqQIEemyx-2w">
    <states xmi:type="State" xmi:id="_OR7BEJpBEeqqQIEemyx-2w" id="Operating" uuid="_OR_5kJpBEeqqQIEemyx-2w"/>
    <states xmi:type="State" xmi:id="_PQ5KwJpBEeqqQIEemyx-2w" id="Maneuvering" uuid="_PQ5KwZpBEeqqQIEemyx-2w"/>
    <states xmi:type="PseudoState" xmi:id="_QweuMJpBEeqqQIEemyx-2w" id="Entry State Pointer" type="entry"/>
    <transitions xmi:type="Transition" xmi:id="_RRUxcJpBEeqqQIEemyx-2w" from="_QweuMJpBEeqqQIEemyx-2w" to="_PQ5KwJpBEeqqQIEemyx-2w" id="Transition 2" uuid="_RRUxcZpBEeqqQIEemyx-2w"/>
    <transitions xmi:type="Transition" xmi:id="_RyI_gJpBEeqqQIEemyx-2w" from="_PQ5KwJpBEeqqQIEemyx-2w" to="_OR7BEJpBEeqqQIEemyx-2w" messageCheckerClass="Object" id="Transition 3" guard="(agent.isC2ApproachSelector() != null)&amp;&amp;(agent.isTokenUpdated())&amp;&amp;(agent.getToken().getAvailableTasks().size()>0);" uuid="_RyJmkJpBEeqqQIEemyx-2w"/>
    <transitions xmi:type="Transition" xmi:id="_STnU4JpBEeqqQIEemyx-2w" from="_OR7BEJpBEeqqQIEemyx-2w" to="_PQ5KwJpBEeqqQIEemyx-2w" messageCheckerClass="Object" id="Transition 4" guard="(agent.isC2ApproachSelector()!=null)&amp;&amp;(agent.isTokenUpdated())&amp;&amp;(agent.getToken().getCh3()!=null);" uuid="_STnU4ZpBEeqqQIEemyx-2w"/>
  </StateMachine>
  <notation:Diagram xmi:id="_NRA9sJpBEeqqQIEemyx-2w" type="Statechart" element="_NQ9TUZpBEeqqQIEemyx-2w" name="statechart2.rsc" measurementUnit="Pixel">
    <children xmi:type="notation:Shape" xmi:id="_OSC84JpBEeqqQIEemyx-2w" type="2003" element="_OR7BEJpBEeqqQIEemyx-2w" fontName=".AppleSystemUIFont" fontHeight="12" bold="true">
      <children xmi:type="notation:DecorationNode" xmi:id="_OSDj8JpBEeqqQIEemyx-2w" type="5001"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_OSC84ZpBEeqqQIEemyx-2w" x="336" y="180"/>
    </children>
    <children xmi:type="notation:Shape" xmi:id="_PQ5x0JpBEeqqQIEemyx-2w" type="2003" element="_PQ5KwJpBEeqqQIEemyx-2w" fontName=".AppleSystemUIFont" fontHeight="12" bold="true">
      <children xmi:type="notation:DecorationNode" xmi:id="_PQ6Y4ZpBEeqqQIEemyx-2w" type="5001"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_PQ6Y4JpBEeqqQIEemyx-2w" x="576" y="180"/>
    </children>
    <children xmi:type="notation:Shape" xmi:id="_QwgjYJpBEeqqQIEemyx-2w" type="2007" element="_QweuMJpBEeqqQIEemyx-2w" fontName=".AppleSystemUIFont">
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_QwgjYZpBEeqqQIEemyx-2w" x="714" y="79"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_NRA9sZpBEeqqQIEemyx-2w"/>
    <edges xmi:type="notation:Edge" xmi:id="_RRZC4JpBEeqqQIEemyx-2w" type="4001" element="_RRUxcJpBEeqqQIEemyx-2w" source="_QwgjYJpBEeqqQIEemyx-2w" target="_PQ5x0JpBEeqqQIEemyx-2w">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_RRZC4ZpBEeqqQIEemyx-2w"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_RRZC4ppBEeqqQIEemyx-2w" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_RRZC45pBEeqqQIEemyx-2w" points="[0, 0, 114, -111]$[-135, 91, -21, -20]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_RRbfIJpBEeqqQIEemyx-2w" id="CENTER"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_RyKNoJpBEeqqQIEemyx-2w" type="4001" element="_RyI_gJpBEeqqQIEemyx-2w" source="_PQ5x0JpBEeqqQIEemyx-2w" target="_OSC84JpBEeqqQIEemyx-2w">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_RyKNoZpBEeqqQIEemyx-2w"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_RyKNoppBEeqqQIEemyx-2w" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_RyKNo5pBEeqqQIEemyx-2w" points="[-47, 7, 193, 0]$[-231, 0, 9, -7]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_RyMC0JpBEeqqQIEemyx-2w" id="(0.6811594202898551,0.0)"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_RyMC0ZpBEeqqQIEemyx-2w" id="(0.8392857142857143,0.175)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_STojAJpBEeqqQIEemyx-2w" type="4001" element="_STnU4JpBEeqqQIEemyx-2w" source="_OSC84JpBEeqqQIEemyx-2w" target="_PQ5x0JpBEeqqQIEemyx-2w">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_STojAZpBEeqqQIEemyx-2w"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_STojAppBEeqqQIEemyx-2w" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_STojA5pBEeqqQIEemyx-2w" points="[0, 5, -192, 0]$[184, 7, -8, 2]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_STpxIJpBEeqqQIEemyx-2w" id="(1.0,0.575)"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_STpxIZpBEeqqQIEemyx-2w" id="(0.11594202898550725,0.7)"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>
