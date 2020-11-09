<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns="http://repast.sf.net/statecharts" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.2/notation">
  <StateMachine xmi:id="_fiWckJSaEeqUuepXYUrgtg" agentType="entities.Drone" package="roles.chart" className="EX" nextID="91" id="EX" uuid="_fiV1gJSaEeqUuepXYUrgtg">
    <states xmi:type="State" xmi:id="_iY3WsJSaEeqUuepXYUrgtg" id="Idle" uuid="_iY3WsZSaEeqUuepXYUrgtg"/>
    <states xmi:type="State" xmi:id="_pSQQYJSaEeqUuepXYUrgtg" id="Running" uuid="_pSQQYZSaEeqUuepXYUrgtg"/>
    <states xmi:type="PseudoState" xmi:id="_SGpP8JSbEeqUuepXYUrgtg" id="Entry State Pointer" type="entry"/>
    <states xmi:type="State" xmi:id="_6_P58JTDEeqUuepXYUrgtg" id="Reallocating" uuid="_6_SWMJTDEeqUuepXYUrgtg"/>
    <states xmi:type="State" xmi:id="_FyTkoJYIEeqUuepXYUrgtg" id="Reporting" onEnter="" uuid="_FyWA4JYIEeqUuepXYUrgtg"/>
    <transitions xmi:type="Transition" xmi:id="_AjNOYJSbEeqUuepXYUrgtg" from="_iY3WsJSaEeqUuepXYUrgtg" to="_pSQQYJSaEeqUuepXYUrgtg" messageCheckerClass="Object" id="Transition 12" guard="((agent.isExecutor() != null)&amp;&amp;(agent.isExecutor().getAllocatedTasks()!=null)&amp;&amp;(agent.isExecutor().getAllocatedTasks().size()>0));" uuid="_AjNOYZSbEeqUuepXYUrgtg"/>
    <transitions xmi:type="Transition" xmi:id="_SlqHMJSbEeqUuepXYUrgtg" from="_SGpP8JSbEeqUuepXYUrgtg" to="_iY3WsJSaEeqUuepXYUrgtg" id="Transition 15" uuid="_SlqHMZSbEeqUuepXYUrgtg"/>
    <transitions xmi:type="Transition" xmi:id="_FCYoQJTDEeqUuepXYUrgtg" from="_pSQQYJSaEeqUuepXYUrgtg" to="_iY3WsJSaEeqUuepXYUrgtg" id="Transition 34" guard="((agent.isExecutor()!=null)&amp;&amp;((agent.isExecutor().getAllocatedTasks()==null)||(agent.isExecutor().getAllocatedTasks().size()==0)));" uuid="_FCbEgJTDEeqUuepXYUrgtg"/>
    <transitions xmi:type="Transition" xmi:id="_-46TIJTDEeqUuepXYUrgtg" from="_pSQQYJSaEeqUuepXYUrgtg" to="_6_P58JTDEeqUuepXYUrgtg" messageCheckerClass="Object" id="Transition 39" guard="((agent.isExecutor() != null)&amp;&amp;(agent.isExecutor().isReallocating()));" uuid="_-46TIZTDEeqUuepXYUrgtg"/>
    <transitions xmi:type="Transition" xmi:id="_LU_t8JYEEeqUuepXYUrgtg" from="_6_P58JTDEeqUuepXYUrgtg" to="_pSQQYJSaEeqUuepXYUrgtg" id="Transition 55" guard="((agent.isExecutor() != null)&amp;&amp;(!agent.isExecutor().isReallocating()));" uuid="_LVCKMJYEEeqUuepXYUrgtg"/>
    <transitions xmi:type="Transition" xmi:id="_OxhiYJYIEeqUuepXYUrgtg" from="_pSQQYJSaEeqUuepXYUrgtg" to="_FyTkoJYIEeqUuepXYUrgtg" messageCheckerClass="Object" id="Transition 60" guard="(agent.isExecutor()!=null)&amp;&amp;(agent.isExecutor().getFinishedTask()!=null);" uuid="_OxhiYZYIEeqUuepXYUrgtg"/>
    <transitions xmi:type="Transition" xmi:id="_Q-d7EJYIEeqUuepXYUrgtg" from="_FyTkoJYIEeqUuepXYUrgtg" to="_pSQQYJSaEeqUuepXYUrgtg" messageCheckerClass="Object" id="Transition 61" guard="(agent.isExecutor()!=null)&amp;&amp;(agent.isExecutor().getFinishedTask()==null);" uuid="_Q-d7EZYIEeqUuepXYUrgtg"/>
  </StateMachine>
  <notation:Diagram xmi:id="_fi_VwJSaEeqUuepXYUrgtg" type="Statechart" element="_fiWckJSaEeqUuepXYUrgtg" name="statechart.rsc" measurementUnit="Pixel">
    <children xmi:type="notation:Shape" xmi:id="_iY8PMJSaEeqUuepXYUrgtg" type="2003" element="_iY3WsJSaEeqUuepXYUrgtg" fontName=".AppleSystemUIFont" fontHeight="12" bold="true">
      <children xmi:type="notation:DecorationNode" xmi:id="_iY-rcJSaEeqUuepXYUrgtg" type="5001"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_iY8PMZSaEeqUuepXYUrgtg" x="696" y="184" width="97"/>
    </children>
    <children xmi:type="notation:Shape" xmi:id="_pSQ3cJSaEeqUuepXYUrgtg" type="2003" element="_pSQQYJSaEeqUuepXYUrgtg" fontName=".AppleSystemUIFont" fontHeight="12" bold="true">
      <children xmi:type="notation:DecorationNode" xmi:id="_pSRegJSaEeqUuepXYUrgtg" type="5001"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_pSQ3cZSaEeqUuepXYUrgtg" x="476" y="184" width="101"/>
    </children>
    <children xmi:type="notation:Shape" xmi:id="_SGuIcJSbEeqUuepXYUrgtg" type="2007" element="_SGpP8JSbEeqUuepXYUrgtg" fontName=".AppleSystemUIFont" fontHeight="12">
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_SGuIcZSbEeqUuepXYUrgtg" x="864" y="84"/>
    </children>
    <children xmi:type="notation:Shape" xmi:id="_6_S9QJTDEeqUuepXYUrgtg" type="2003" element="_6_P58JTDEeqUuepXYUrgtg" fontName=".AppleSystemUIFont" fontHeight="12" bold="true">
      <children xmi:type="notation:DecorationNode" xmi:id="_6_S9QpTDEeqUuepXYUrgtg" type="5001"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_6_S9QZTDEeqUuepXYUrgtg" x="180" y="184" width="157"/>
    </children>
    <children xmi:type="notation:Shape" xmi:id="_FyWn8JYIEeqUuepXYUrgtg" type="2003" element="_FyTkoJYIEeqUuepXYUrgtg" fontName=".AppleSystemUIFont" fontHeight="12" bold="true">
      <children xmi:type="notation:DecorationNode" xmi:id="_FyWn8pYIEeqUuepXYUrgtg" type="5001"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_FyWn8ZYIEeqUuepXYUrgtg" x="456" y="348" width="145"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_fi_VwZSaEeqUuepXYUrgtg"/>
    <edges xmi:type="notation:Edge" xmi:id="_AjN1cJSbEeqUuepXYUrgtg" type="4001" element="_AjNOYJSbEeqUuepXYUrgtg" source="_iY8PMJSaEeqUuepXYUrgtg" target="_pSQ3cJSaEeqUuepXYUrgtg">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_AjN1cZSbEeqUuepXYUrgtg"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_AjN1cpSbEeqUuepXYUrgtg" fontName=".AppleSystemUIFont" fontHeight="12"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_AjN1c5SbEeqUuepXYUrgtg" points="[-40, 6, 143, 0]$[-176, 0, 7, -6]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_AjPDkJSbEeqUuepXYUrgtg" id="(1.0,0.0)"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_AjPDkZSbEeqUuepXYUrgtg" id="(0.8541666666666666,0.15)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_SlquQJSbEeqUuepXYUrgtg" type="4001" element="_SlqHMJSbEeqUuepXYUrgtg" source="_SGuIcJSbEeqUuepXYUrgtg" target="_iY8PMJSaEeqUuepXYUrgtg">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_SlquQZSbEeqUuepXYUrgtg"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_SlquQpSbEeqUuepXYUrgtg" fontName=".AppleSystemUIFont" fontHeight="12"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_SlquQ5SbEeqUuepXYUrgtg" points="[0, 0, 50, -107]$[-53, 87, -3, -20]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_Slr8YJSbEeqUuepXYUrgtg" id="CENTER"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_D8kpsJYWEeqUuepXYUrgtg" id="(0.979381443298969,0.025)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_FCbrkJTDEeqUuepXYUrgtg" type="4001" element="_FCYoQJTDEeqUuepXYUrgtg" source="_pSQ3cJSaEeqUuepXYUrgtg" target="_iY8PMJSaEeqUuepXYUrgtg">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_FCbrkZTDEeqUuepXYUrgtg"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_FCbrkpTDEeqUuepXYUrgtg" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_FCbrk5TDEeqUuepXYUrgtg" points="[0, 1, -128, 0]$[125, 3, -3, 2]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_FCc5sJTDEeqUuepXYUrgtg" id="(1.0,0.85)"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_FCc5sZTDEeqUuepXYUrgtg" id="(0.075,0.875)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_-466MJTDEeqUuepXYUrgtg" type="4001" element="_-46TIJTDEeqUuepXYUrgtg" source="_pSQ3cJSaEeqUuepXYUrgtg" target="_6_S9QJTDEeqUuepXYUrgtg">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_-466MZTDEeqUuepXYUrgtg"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_-466MpTDEeqUuepXYUrgtg" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_-466M5TDEeqUuepXYUrgtg" points="[0, 1, 146, 0]$[-136, 7, 10, 6]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_-48IUJTDEeqUuepXYUrgtg" id="(0.0,0.175)"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_-48IUZTDEeqUuepXYUrgtg" id="(0.9634146341463414,0.25)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_LVCxQJYEEeqUuepXYUrgtg" type="4001" element="_LU_t8JYEEeqUuepXYUrgtg" source="_6_S9QJTDEeqUuepXYUrgtg" target="_pSQ3cJSaEeqUuepXYUrgtg">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_LVCxQZYEEeqUuepXYUrgtg"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_LVCxQpYEEeqUuepXYUrgtg" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_LVCxQ5YEEeqUuepXYUrgtg" points="[20, -7, -135, 0]$[150, -5, -5, 2]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_LVDYUJYEEeqUuepXYUrgtg" id="(0.7560975609756098,1.0)"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_LVD_YJYEEeqUuepXYUrgtg" id="(0.0847457627118644,0.825)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_OxiJcJYIEeqUuepXYUrgtg" type="4001" element="_OxhiYJYIEeqUuepXYUrgtg" source="_pSQ3cJSaEeqUuepXYUrgtg" target="_FyWn8JYIEeqUuepXYUrgtg">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_OxiJcZYIEeqUuepXYUrgtg"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_OxiJcpYIEeqUuepXYUrgtg" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_OxiJc5YIEeqUuepXYUrgtg" points="[0, 0, 22, -100]$[-22, 100, 0, 0]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_OxjXkJYIEeqUuepXYUrgtg" id="(0.3050847457627119,0.925)"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_OxjXkZYIEeqUuepXYUrgtg" id="(0.2896551724137931,0.05)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_Q-eiIJYIEeqUuepXYUrgtg" type="4001" element="_Q-d7EJYIEeqUuepXYUrgtg" source="_FyWn8JYIEeqUuepXYUrgtg" target="_pSQ3cJSaEeqUuepXYUrgtg">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_Q-eiIZYIEeqUuepXYUrgtg"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_Q-eiIpYIEeqUuepXYUrgtg" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_Q-eiI5YIEeqUuepXYUrgtg" points="[-5, -35, 15, 107]$[-11, -135, 9, 7]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_Q-fwQJYIEeqUuepXYUrgtg" id="(0.7517241379310344,0.05)"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_Q-fwQZYIEeqUuepXYUrgtg" id="(0.8135593220338984,0.825)"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>
