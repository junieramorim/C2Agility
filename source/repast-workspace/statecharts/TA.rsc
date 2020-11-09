<?xml version="1.0" encoding="UTF-8"?>
<xmi:XMI xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns="http://repast.sf.net/statecharts" xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.2/notation">
  <StateMachine xmi:id="_vnZWEJciEeqUuepXYUrgtg" agentType="entities.Drone" package="entities.chart" className="TA" nextID="175" id="TA" uuid="_vnYvAJciEeqUuepXYUrgtg">
    <states xmi:type="State" xmi:id="_0qv7MJciEeqUuepXYUrgtg" id="Waiting" uuid="_0qxwYJciEeqUuepXYUrgtg"/>
    <states xmi:type="PseudoState" xmi:id="_6ZkfMJciEeqUuepXYUrgtg" id="Entry State Pointer" type="entry"/>
    <states xmi:type="State" xmi:id="_7fovYJciEeqUuepXYUrgtg" id="Ready" uuid="_7fovYZciEeqUuepXYUrgtg"/>
    <states xmi:type="State" xmi:id="_mPmzIJfZEeq49tuNB7DUMA" id="Allocating" uuid="_mPooUJfZEeq49tuNB7DUMA"/>
    <states xmi:type="State" xmi:id="_wvrfoJfZEeq49tuNB7DUMA" id="Updating" uuid="_wvrfoZfZEeq49tuNB7DUMA"/>
    <states xmi:type="State" xmi:id="_fmU8IJhUEeq49tuNB7DUMA" id="Notifying" onExit="" uuid="_fmXYYJhUEeq49tuNB7DUMA"/>
    <states xmi:type="State" xmi:id="_3Rc_8JicEeq49tuNB7DUMA" id="Binding" onEnter="" onExit="" uuid="_3Re1IJicEeq49tuNB7DUMA"/>
    <transitions xmi:type="Transition" xmi:id="_68xS4JciEeqUuepXYUrgtg" from="_6ZkfMJciEeqUuepXYUrgtg" to="_0qv7MJciEeqUuepXYUrgtg" id="Transition 1" uuid="_68xS4ZciEeqUuepXYUrgtg"/>
    <transitions xmi:type="Transition" xmi:id="__UkZkJciEeqUuepXYUrgtg" from="_0qv7MJciEeqUuepXYUrgtg" to="_7fovYJciEeqUuepXYUrgtg" messageCheckerClass="Object" id="Transition 3" guard="(agent.isTaskAllocator()!=null)&amp;&amp;(agent.getToken().getAvailableTasks().size()>0);" uuid="__UkZkZciEeqUuepXYUrgtg"/>
    <transitions xmi:type="Transition" xmi:id="_ucZOYJfZEeq49tuNB7DUMA" from="_7fovYJciEeqUuepXYUrgtg" to="_mPmzIJfZEeq49tuNB7DUMA" messageCheckerClass="Object" id="Transition 7" guard="(agent.isTaskAllocator() != null)&amp;&amp;(agent.isTokenUpdated())&amp;&amp;(agent.getToken().getAvailableTasks()!=null)&amp;&amp;(agent.getToken().getAvailableTasks().size()>0)&amp;&amp;(agent.isTaskAllocator().isAllocating());" uuid="_ucZ1cJfZEeq49tuNB7DUMA"/>
    <transitions xmi:type="Transition" xmi:id="_2mPDUJfZEeq49tuNB7DUMA" from="_7fovYJciEeqUuepXYUrgtg" to="_wvrfoJfZEeq49tuNB7DUMA" messageCheckerClass="Object" id="Transition 9" guard="(agent.isTaskAllocator() != null)&amp;&amp;(agent.isTokenUpdated())&amp;&amp;(agent.getToken().getCh1().size()>0);" uuid="_2mPqYJfZEeq49tuNB7DUMA"/>
    <transitions xmi:type="Transition" xmi:id="_YRIFoJhTEeq49tuNB7DUMA" from="_wvrfoJfZEeq49tuNB7DUMA" to="_7fovYJciEeqUuepXYUrgtg" messageCheckerClass="Object" id="Transition 31" guard="&#x9;(agent.isTaskAllocator() != null)&amp;&amp;(agent.isTokenUpdated())&amp;&amp;(agent.getToken().getCh1().size()==0);" uuid="_YRIFoZhTEeq49tuNB7DUMA"/>
    <transitions xmi:type="Transition" xmi:id="_w2-kkJhUEeq49tuNB7DUMA" from="_mPmzIJfZEeq49tuNB7DUMA" to="_fmU8IJhUEeq49tuNB7DUMA" messageCheckerClass="Object" id="Transition 40" guard="(agent.isTaskAllocator() != null)&amp;&amp;(agent.isTokenUpdated())&amp;&amp;(agent.getToken().getAllocations()!= null)&amp;&amp;(agent.getToken().getAllocations().size()>0);" uuid="_w2-kkZhUEeq49tuNB7DUMA"/>
    <transitions xmi:type="Transition" xmi:id="_65T3cJicEeq49tuNB7DUMA" from="_fmU8IJhUEeq49tuNB7DUMA" to="_3Rc_8JicEeq49tuNB7DUMA" messageCheckerClass="Object" id="Transition 84" guard="(agent.isTaskAllocator() != null)&amp;&amp;(agent.isTaskAllocator().isAllocating());" uuid="_65T3cZicEeq49tuNB7DUMA"/>
    <transitions xmi:type="Transition" xmi:id="_lTQDoJkNEeq49tuNB7DUMA" from="_3Rc_8JicEeq49tuNB7DUMA" to="_fmU8IJhUEeq49tuNB7DUMA" messageCheckerClass="Object" id="Transition 133" guard="false;" uuid="_lTSf4JkNEeq49tuNB7DUMA"/>
    <transitions xmi:type="Transition" xmi:id="_CVZx8JkTEeq49tuNB7DUMA" from="_3Rc_8JicEeq49tuNB7DUMA" to="_7fovYJciEeqUuepXYUrgtg" messageCheckerClass="Object" id="Transition 137" guard="&#x9;(agent.isTaskAllocator() != null)&amp;&amp;(agent.getTokenState()==agent.getTokenState().NOT_RECEIVED);" uuid="_CVbAEJkTEeq49tuNB7DUMA"/>
    <transitions xmi:type="Transition" xmi:id="__zqIIJl5EeqQ1p0AkO9stQ" from="_mPmzIJfZEeq49tuNB7DUMA" to="_0qv7MJciEeqUuepXYUrgtg" messageCheckerClass="Object" id="Transition 171" guard="false;" uuid="__ztLcJl5EeqQ1p0AkO9stQ"/>
  </StateMachine>
  <notation:Diagram xmi:id="_vnZ9IJciEeqUuepXYUrgtg" type="Statechart" element="_vnZWEJciEeqUuepXYUrgtg" name="statechart1.rsc" measurementUnit="Pixel">
    <children xmi:type="notation:Shape" xmi:id="_0qyXcJciEeqUuepXYUrgtg" type="2003" element="_0qv7MJciEeqUuepXYUrgtg" fontName=".AppleSystemUIFont" fontHeight="12" bold="true">
      <children xmi:type="notation:DecorationNode" xmi:id="_0qyXcpciEeqUuepXYUrgtg" type="5001"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_0qyXcZciEeqUuepXYUrgtg" x="407" y="96" width="150"/>
    </children>
    <children xmi:type="notation:Shape" xmi:id="_6ZlGQJciEeqUuepXYUrgtg" type="2007" element="_6ZkfMJciEeqUuepXYUrgtg" fontName=".AppleSystemUIFont">
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_6ZlGQZciEeqUuepXYUrgtg" x="472" y="23"/>
    </children>
    <children xmi:type="notation:Shape" xmi:id="_7fpWcJciEeqUuepXYUrgtg" type="2003" element="_7fovYJciEeqUuepXYUrgtg" fontName=".AppleSystemUIFont" fontHeight="12" bold="true">
      <children xmi:type="notation:DecorationNode" xmi:id="_7fp9gJciEeqUuepXYUrgtg" type="5001"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_7fpWcZciEeqUuepXYUrgtg" x="310" y="216" width="121"/>
    </children>
    <children xmi:type="notation:Shape" xmi:id="_mPrroJfZEeq49tuNB7DUMA" type="2003" element="_mPmzIJfZEeq49tuNB7DUMA" fontName=".AppleSystemUIFont" fontHeight="12" bold="true">
      <children xmi:type="notation:DecorationNode" xmi:id="_mPsSsJfZEeq49tuNB7DUMA" type="5001"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_mPrroZfZEeq49tuNB7DUMA" x="564" y="216" width="217"/>
    </children>
    <children xmi:type="notation:Shape" xmi:id="_wvstwJfZEeq49tuNB7DUMA" type="2003" element="_wvrfoJfZEeq49tuNB7DUMA" fontName=".AppleSystemUIFont" fontHeight="12" bold="true">
      <children xmi:type="notation:DecorationNode" xmi:id="_wvstwpfZEeq49tuNB7DUMA" type="5001"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_wvstwZfZEeq49tuNB7DUMA" x="298" y="396" width="145"/>
    </children>
    <children xmi:type="notation:Shape" xmi:id="_fmX_cJhUEeq49tuNB7DUMA" type="2003" element="_fmU8IJhUEeq49tuNB7DUMA" fontName=".AppleSystemUIFont" fontHeight="12" bold="true">
      <children xmi:type="notation:DecorationNode" xmi:id="_fmX_cphUEeq49tuNB7DUMA" type="5001"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_fmX_cZhUEeq49tuNB7DUMA" x="708" y="348" width="157"/>
    </children>
    <children xmi:type="notation:Shape" xmi:id="_3RfcMJicEeq49tuNB7DUMA" type="2003" element="_3Rc_8JicEeq49tuNB7DUMA" fontName=".AppleSystemUIFont" fontHeight="12" bold="true">
      <children xmi:type="notation:DecorationNode" xmi:id="_3RfcMpicEeq49tuNB7DUMA" type="5001"/>
      <layoutConstraint xmi:type="notation:Bounds" xmi:id="_3RfcMZicEeq49tuNB7DUMA" x="552" y="468" width="145"/>
    </children>
    <styles xmi:type="notation:DiagramStyle" xmi:id="_vnZ9IZciEeqUuepXYUrgtg"/>
    <edges xmi:type="notation:Edge" xmi:id="_68x58JciEeqUuepXYUrgtg" type="4001" element="_68xS4JciEeqUuepXYUrgtg" source="_6ZlGQJciEeqUuepXYUrgtg" target="_0qyXcJciEeqUuepXYUrgtg">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_68x58ZciEeqUuepXYUrgtg"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_68x58pciEeqUuepXYUrgtg" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_68x585ciEeqUuepXYUrgtg" points="[0, 0, 5, -83]$[3, 63, 8, -20]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_68zIEJciEeqUuepXYUrgtg" id="CENTER"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="__UlAoJciEeqUuepXYUrgtg" type="4001" element="__UkZkJciEeqUuepXYUrgtg" source="_0qyXcJciEeqUuepXYUrgtg" target="_7fpWcJciEeqUuepXYUrgtg">
      <styles xmi:type="notation:RoutingStyle" xmi:id="__UlAoZciEeqUuepXYUrgtg"/>
      <styles xmi:type="notation:FontStyle" xmi:id="__UlAopciEeqUuepXYUrgtg" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="__UlAo5ciEeqUuepXYUrgtg" points="[0, 0, 135, -100]$[-136, 80, -1, -20]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="__UmOwJciEeqUuepXYUrgtg" id="(0.07692307692307693,0.925)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_uceG4JfZEeq49tuNB7DUMA" type="4001" element="_ucZOYJfZEeq49tuNB7DUMA" source="_7fpWcJciEeqUuepXYUrgtg" target="_mPrroJfZEeq49tuNB7DUMA">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_uceG4ZfZEeq49tuNB7DUMA"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_uceG4pfZEeq49tuNB7DUMA" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_uceG45fZEeq49tuNB7DUMA" points="[0, -5, -167, 0]$[155, -3, -12, 2]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_ucf8EJfZEeq49tuNB7DUMA" id="(0.9647058823529412,0.25)"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_ucf8EZfZEeq49tuNB7DUMA" id="(0.006369426751592357,0.475)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_2mQRcJfZEeq49tuNB7DUMA" type="4001" element="_2mPDUJfZEeq49tuNB7DUMA" source="_7fpWcJciEeqUuepXYUrgtg" target="_wvstwJfZEeq49tuNB7DUMA">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_2mQRcZfZEeq49tuNB7DUMA"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_2mQRcpfZEeq49tuNB7DUMA" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_2mQRc5fZEeq49tuNB7DUMA" points="[-5, 6, 0, -147]$[12, 146, 17, -7]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_2mSGoJfZEeq49tuNB7DUMA" id="(0.7411764705882353,0.975)"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_2mSGoZfZEeq49tuNB7DUMA" id="(0.635593220338983,0.025)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_YRJTwJhTEeq49tuNB7DUMA" type="4001" element="_YRIFoJhTEeq49tuNB7DUMA" source="_wvstwJfZEeq49tuNB7DUMA" target="_7fpWcJciEeqUuepXYUrgtg">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_YRJTwZhTEeq49tuNB7DUMA"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_YRJTwphTEeq49tuNB7DUMA" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_YRJTw5hTEeq49tuNB7DUMA" points="[-5, 0, 0, 144]$[-9, -140, -4, 4]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_YRKh4JhTEeq49tuNB7DUMA" id="(0.3813559322033898,0.0)"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_YRKh4ZhTEeq49tuNB7DUMA" id="(0.32941176470588235,0.9)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_w2_LoJhUEeq49tuNB7DUMA" type="4001" element="_w2-kkJhUEeq49tuNB7DUMA" source="_mPrroJfZEeq49tuNB7DUMA" target="_fmX_cJhUEeq49tuNB7DUMA">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_w2_LoZhUEeq49tuNB7DUMA"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_w2_LophUEeq49tuNB7DUMA" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_w2_Lo5hUEeq49tuNB7DUMA" points="[0, 0, -99, -98]$[95, 92, -4, -6]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_w2_ysJhUEeq49tuNB7DUMA" id="(0.8387096774193549,1.0)"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_w3AZwJhUEeq49tuNB7DUMA" id="(0.4338235294117647,0.15)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_65UegJicEeq49tuNB7DUMA" type="4001" element="_65T3cJicEeq49tuNB7DUMA" source="_fmX_cJhUEeq49tuNB7DUMA" target="_3RfcMJicEeq49tuNB7DUMA">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_65UegZicEeq49tuNB7DUMA"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_65UegpicEeq49tuNB7DUMA" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_65Ueg5icEeq49tuNB7DUMA" points="[-10, 4, 194, -85]$[-203, 84, 1, -5]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_65VFkJicEeq49tuNB7DUMA" id="(0.3602941176470588,0.775)"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_65VsoJicEeq49tuNB7DUMA" id="(0.5289256198347108,0.125)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_lTTG8JkNEeq49tuNB7DUMA" type="4001" element="_lTQDoJkNEeq49tuNB7DUMA" source="_3RfcMJicEeq49tuNB7DUMA" target="_fmX_cJhUEeq49tuNB7DUMA">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_lTTG8ZkNEeq49tuNB7DUMA"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_lTTG8pkNEeq49tuNB7DUMA" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_lTTG85kNEeq49tuNB7DUMA" points="[0, 0, -141, 116]$[147, -115, 6, 1]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_lTTuAJkNEeq49tuNB7DUMA" id="(1.0,0.85)"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_lTTuAZkNEeq49tuNB7DUMA" id="(0.9558823529411765,0.95)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="_CVbnIJkTEeq49tuNB7DUMA" type="4001" element="_CVZx8JkTEeq49tuNB7DUMA" source="_3RfcMJicEeq49tuNB7DUMA" target="_7fpWcJciEeqUuepXYUrgtg">
      <styles xmi:type="notation:RoutingStyle" xmi:id="_CVbnIZkTEeq49tuNB7DUMA"/>
      <styles xmi:type="notation:FontStyle" xmi:id="_CVbnIpkTEeq49tuNB7DUMA" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="_CVbnI5kTEeq49tuNB7DUMA" points="[-43, -36, 260, 220]$[-288, -250, 15, 6]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="_CVcOMJkTEeq49tuNB7DUMA" id="(0.2975206611570248,0.0)"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="_CVcOMZkTEeq49tuNB7DUMA" id="(0.8235294117647058,0.8)"/>
    </edges>
    <edges xmi:type="notation:Edge" xmi:id="__zyD8Jl5EeqQ1p0AkO9stQ" type="4001" element="__zqIIJl5EeqQ1p0AkO9stQ" source="_mPrroJfZEeq49tuNB7DUMA" target="_0qyXcJciEeqUuepXYUrgtg">
      <styles xmi:type="notation:RoutingStyle" xmi:id="__zyD8Zl5EeqQ1p0AkO9stQ"/>
      <styles xmi:type="notation:FontStyle" xmi:id="__zyD8pl5EeqQ1p0AkO9stQ" fontName=".AppleSystemUIFont"/>
      <bendpoints xmi:type="notation:RelativeBendpoints" xmi:id="__zyD85l5EeqQ1p0AkO9stQ" points="[-62, -33, 182, 97]$[-224, -129, 20, 1]"/>
      <sourceAnchor xmi:type="notation:IdentityAnchor" xmi:id="__zz5IJl5EeqQ1p0AkO9stQ" id="(1.0,0.825)"/>
      <targetAnchor xmi:type="notation:IdentityAnchor" xmi:id="__z0gMJl5EeqQ1p0AkO9stQ" id="(0.8666666666666667,0.575)"/>
    </edges>
  </notation:Diagram>
</xmi:XMI>
