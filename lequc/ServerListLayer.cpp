//
//  ServerList.cpp
//  SuperWarriors
//
//  Created by lequ on 14-5-8.
//
//

#include "ServerListLayer.h"
#include "DialogPrompt.h"
#include "WPlatform.h"
#include <math.h>
#include "../external/json/document.h"
#include "../external/json/writer.h"
#include "../external/json/stringbuffer.h"
static const std::string GETPAGE_URL =      "getserver.do";
ServerListLayer* ServerListLayer::create()
{
    ServerListLayer *ret = new ServerListLayer();
    if (ret && ret->init()){
        ret->autorelease();
        return ret;
    } else {
        CC_SAFE_DELETE(ret);
        return nullptr;
    }
}
ServerListLayer::~ServerListLayer()
{
    if (this->sServerPageList) {
        delete this->sServerPageList;
    }
}

bool ServerListLayer::init()
{
    this->sServerPageList = new std::vector<sServerPage> ();
    auto dispatcher = Director::getInstance()->getEventDispatcher();
    auto touchListener = EventListenerTouchOneByOne::create();
    touchListener->onTouchBegan = CC_CALLBACK_2(ServerListLayer::onTouchBegan,this);
    touchListener->onTouchMoved = CC_CALLBACK_2(ServerListLayer::onTouchMoved,this);
    touchListener->onTouchEnded = CC_CALLBACK_2(ServerListLayer::onTouchEnded, this);
    touchListener->setSwallowTouches(true);
    dispatcher->addEventListenerWithSceneGraphPriority(touchListener, this);
    //dispatcher->setPriority(touchListener, -1);
    
    SpriteFrameCache::getInstance()->addSpriteFramesWithFile("files/ccbiui/ccbires/resv4/V4LOGIN.plist");
    SpriteFrameCache::getInstance()->addSpriteFramesWithFile("files/ccbiui/ccbires/resv4/V4UNIVERSAL.plist");
    
    Size visibleSize =  Director::getInstance()->getVisibleSize();

    cocos2d::ui::Scale9Sprite *tbg = cocos2d::ui::Scale9Sprite::createWithSpriteFrameName("dl_di.png");
    tbg->setAnchorPoint(Point(0.5,0));
    tbg->setPosition(Point(visibleSize.width/2, 545));
    tbg->setContentSize(Size(545,65));
    this->addChild(tbg);
    cocos2d::ui::Scale9Sprite *sbg = cocos2d::ui::Scale9Sprite::createWithSpriteFrameName("dl_di.png");
    sbg->setAnchorPoint(Point(0.5,0));
    sbg->setPosition(Point(visibleSize.width/2, 50.0));
    sbg->setContentSize(Size(545,428));
    this->addChild(sbg);
    
//    Sprite *bgD = Sprite::createWithSpriteFrameName("zc_di.png");
//    bgD->setAnchorPoint(Point(0.5,0));
//    bgD->setPosition(Point(visibleSize.width/2, 50.0));
//    bgD->setContentSize(Size(545,465));
//    this->addChild(bgD);

    auto labelNameZJ = Label::createWithSystemFont(WPlatform::getInstance()->getTipInfo(604354), FONENAME, 30);
    //auto labelNameZJ = Label::createWithTTF("最近登录",TTF_YOUYUAN, 30);
    labelNameZJ->setPosition(Point(visibleSize.width/2,630));
    labelNameZJ->setAnchorPoint(Point(0.5, 0.5));
    this->addChild(labelNameZJ);

    auto labelName = Label::createWithSystemFont(WPlatform::getInstance()->getTipInfo(604355), FONENAME, 30);
    //auto labelName = Label::createWithTTF("所有服务器", TTF_YOUYUAN, 30);
    labelName->setPosition(Point(visibleSize.width/2,499));
    labelName->setAnchorPoint(Point(0.5, 0.5));
    this->addChild(labelName);

    for (int i = 0; i<4 ;i++){
        Sprite *imgHua = Sprite::createWithSpriteFrameName("dl_huawen.png");
        imgHua->setAnchorPoint(Point(0.5,0.5));
        imgHua->setFlippedX(i==1 || i==3);
        switch (i) {
            case 0:{
                Point pos = labelNameZJ->getPosition();
                imgHua->setPosition(Point(pos.x-80,pos.y));
            }break;
            case 1:{
                Point pos = labelNameZJ->getPosition();
                imgHua->setPosition(Point(pos.x+80,pos.y));
            }break;
            case 2:{
                Point pos = labelName->getPosition();
                imgHua->setPosition(Point(pos.x-110,pos.y));
            }break;
            case 3:{
                Point pos = labelName->getPosition();
                imgHua->setPosition(Point(pos.x+110,pos.y));
            }break;
        }
        this->addChild(imgHua);
    }
    
    return true;
}

void ServerListLayer::setRecently(std::string slistid)
{
    if(slistid.length() == 0)
    {
        return;
    }
    
    std::string o_str = slistid;
	std::vector<std::string> str_list; // 存放分割后的字符串
	int comma_n = 0;
	do
	{
		std::string tmp_s = "";
		comma_n = o_str.find( "_" );
		if( -1 == comma_n )
		{
			tmp_s = o_str.substr( 0, o_str.length() );
			str_list.push_back( tmp_s );
			break;
		}
		tmp_s = o_str.substr( 0, comma_n );
		o_str.erase( 0, comma_n+1 );
		str_list.push_back( tmp_s );
	}
	while(true);

    for (int i=0; i<str_list.size(); i++) {
        for(int j = 0 ; j < this->sServerPageList->at(this->indexPage).serverlist.size() ; j++){
            std::string tem = this->sServerPageList->at(this->indexPage).serverlist[j].sid.c_str();
            if(tem.compare(str_list[i].c_str()) == 0){
                
                CCLOG("上次登陆的服务器名称:%s",this->sServerPageList->at(this->indexPage).serverlist[j].sname.c_str());
                auto item= MenuItemFont::create(this->sServerPageList->at(this->indexPage).serverlist[j].sname.c_str(),                                                                              CC_CALLBACK_1(ServerListLayer::onRecentlyCallback, this));
                this->vectorSlist = this->sServerPageList->at(this->indexPage).serverlist[j];
                item->setFontNameObj(FONENAME);
                item->setFontSizeObj(21);
                item->setTag(j);
                
                //1推2热3流4新5预0维
                Color3B color;
                if(this->sServerPageList->at(this->indexPage).serverlist[j].status == 1) {
                    color = SERVER_NAME_COLOR_ST1;
                }else if(this->sServerPageList->at(this->indexPage).serverlist[j].status == 2) {
                    color = SERVER_NAME_COLOR_ST2;
                }else if(this->sServerPageList->at(this->indexPage).serverlist[j].status == 3) {
                    color = SERVER_NAME_COLOR_ST3;
                }else if(this->sServerPageList->at(this->indexPage).serverlist[j].status == 4) {
                    color = SERVER_NAME_COLOR_ST4;
                }else if(this->sServerPageList->at(this->indexPage).serverlist[j].status == 5) {
                    color = SERVER_NAME_COLOR_ST5;
                }else if(this->sServerPageList->at(this->indexPage).serverlist[j].status == 0) {
                    color = SERVER_NAME_COLOR_ST0;
                }else{
                    color = SERVER_NAME_COLOR_ST0;
                }

                item->setColor(color);
                Size visibleSize =  Director::getInstance()->getVisibleSize();
                item->setPosition(Point(visibleSize.width/2-192 + i * 120,580));

                auto menu = Menu::create(item, NULL);
                menu->setPosition(Point::ZERO);
                this->addChild(menu);

                break;
            }
        }
    }
        
    
    
    
//    auto closeItem = MenuItemFont::create("登陆",
//                                          CC_CALLBACK_1(StartLayer::menuLoginCallback, this));
//	closeItem->setPosition(Point(origin.x + visibleSize.width - closeItem->getContentSize().width/2 -100,origin.y + closeItem->getContentSize().height/2+100));
//    
//    auto mregistered = MenuItemFont::create("注册", CC_CALLBACK_1(StartLayer::menuRegistered, this));
//    mregistered->setPosition(Point(120,120));
//    
//    auto menu = Menu::create(closeItem,mregistered, NULL);
//    menu->setPosition(Point::ZERO);
//    this->addChild(menu);

    
}

void ServerListLayer::onRecentlyCallback(Ref* pSender)
{
    MenuItemFont* item = (MenuItemFont*)pSender;
    int tag = item->getTag();
    
    CCLOG("tag = %d",tag);
    if(_delegate){
        serverListType sl = this->sServerPageList->at(this->indexPage).serverlist[tag];//this->vectorSlist
        _delegate->selectServerList(sl);
        //isTouchTable = false;
        _delegate->passServerList();
        this->removeFromParentAndCleanup(true);
    }

}

void ServerListLayer::setSList(std::vector<sServerPage> slsServerPagels)
{
    indexPage = 0;
    for (int i = 0; i < slsServerPagels.size(); i++) {
        this->sServerPageList->push_back(slsServerPagels[i]);
    }
    CCLOG("size=  %lu",this->sServerPageList->at(this->indexPage).serverlist.size());
    for(int i = 0 ; i < this->sServerPageList->at(this->indexPage).serverlist.size() ; i++){
        CCLOG("name = %s  sid = %s",this->sServerPageList->at(this->indexPage).serverlist[i].sname.c_str(),this->sServerPageList->at(this->indexPage).serverlist[i].sid.c_str());
    }
    
    isTouchTable = false;
    Size visibleSize =  Director::getInstance()->getVisibleSize();
    tableView = TableView::create(this, Size(470.0, 360));
	tableView->setDirection(ScrollView::Direction::VERTICAL);
    
	tableView->setPosition(Point(visibleSize.width/2-200,110.0));
	tableView->setDelegate(this);
	//tableView->setVerticalFillOrder(TableView::VerticalFillOrder::TOP_DOWN);
	this->addChild(tableView);
	tableView->reloadData();
    
    tablePageView = TableView::create(this, Size(170.0, 380));
	tablePageView->setDirection(ScrollView::Direction::VERTICAL);
	tablePageView->setPosition(Point(visibleSize.width/2-416,100.0));
	tablePageView->setDelegate(this);
	//tablePageView->setVerticalFillOrder(TableView::VerticalFillOrder::TOP_DOWN);
	this->addChild(tablePageView);
	tablePageView->reloadData();
    
}


bool ServerListLayer::onTouchBegan(Touch* touch, Event  *event){
    Point pt = touch->getLocation();
    touchBegin = pt;
    int size = 50;
    if(pt.x > tableView->getPosition().x - size - 200 &&
       pt.x < tableView->getPosition().x + tableView->getContentSize().width + size*2 &&
       pt.y > tableView->getPosition().y - size &&
       pt.y < tableView->getPosition().y + tableView->getContentSize().height + size*8)
    {
        isTouchTable = true;
    }else{
        isTouchTable = false;
    }
    return true;
}
void ServerListLayer::onTouchMoved(Touch* touch, Event  *event){}
void ServerListLayer::onTouchEnded(Touch* touch, Event  *event)
{
    if(!isTouchTable) {
        _delegate->passServerList();
        this->removeFromParentAndCleanup(true);
    }
    
}
void ServerListLayer::onTouchCancelled(Touch *touch, Event *event){}

void ServerListLayer::tableCellTouched(TableView* table, TableViewCell* cell)
{
    CCLOG("cell touched at index: %ld", cell->getIdx());
    
    Point position = tableView->getPosition();
    Size tableSize = tableView->getContentSize();
    Size visibleSize =  Director::getInstance()->getVisibleSize();
    int idx = cell->getIdx();
    int i = 0;
    if (touchBegin.x > visibleSize.width/2)
    {
        i = 1;
    }
    CCLOG("================touchBegin = %f,%f   i = %d",touchBegin.x,touchBegin.y,i);
    if (table == tableView) {
        CCLOG("tableView touch");
        if(_delegate){
            int sizeList = this->sServerPageList->at(this->indexPage).serverlist.size() - 1;
            int index = 0;
            if (i == 1 && idx == 0 && (sizeList + 1)%2 != 0) {
                return;
            }
            else if(idx != 0 &&(sizeList + 1)%2 != 0)
            {
                index = 1;
            }
            serverListType sl = this->sServerPageList->at(this->indexPage).serverlist[idx * 2 + i - index];
            if(sl.status == 0) {
                DialogPrompt::create()->OnShow(WPlatform::getInstance()->getTipInfo(604356));
            }else if(sl.status == 5) {
                DialogPrompt::create()->OnShow(sl.desc);
            }else{
                _delegate->selectServerList(sl);
                isTouchTable = false;
            }
        }
    }
   else if (table == tablePageView)
   {
       CCLOG("tablePageView touch");
       tableView->reloadData();
       tablePageView->reloadData();
       int lengt = this->sServerPageList->size() - 1;
       this->indexPage = lengt - cell->getIdx(); //lengt - cell->getIdx();
       if (this->sServerPageList->at(this->indexPage).serverlist.size() == 0) {
           if (isLodingData == true) {
               return;
           }
           auto getPageServerDate = _delegate->getServerPage(this->sServerPageList->at(this->indexPage).pageId);
           std::string url = IP_URL + GETPAGE_URL;
           rapidjson::Document fromScratch;
           fromScratch.SetObject();
           rapidjson::Document::AllocatorType& allocator = fromScratch.GetAllocator();
           fromScratch.AddMember("type", rapidjson::StringRef(getPageServerDate.type.c_str()), allocator); // 平台相关
           fromScratch.AddMember("fid", rapidjson::StringRef(getPageServerDate.fid.c_str()), allocator);
           fromScratch.AddMember("userId", rapidjson::StringRef(getPageServerDate.userId.c_str()), allocator);
           fromScratch.AddMember("gameId", rapidjson::StringRef(YOUXIHAO), allocator);
           fromScratch.AddMember("page", getPageServerDate.page, allocator);
           fromScratch.AddMember("userType", USERTYPE, allocator);// 内部0,这个始终传0，如果有特殊需要修改的，直接通知服务端改数据库
           fromScratch.AddMember("version", rapidjson::StringRef(LQVERSION.c_str()), allocator);// build code 数字的
           fromScratch.AddMember("status", 1, allocator);// 1新版本分组服务器
           rapidjson::StringBuffer strbuf;
           rapidjson::Writer<rapidjson::StringBuffer> writer(strbuf);
           fromScratch.Accept(writer);
           printf("请求列表(2)--\n%s\n--\n", strbuf.GetString());
           sendHTTP(url, strbuf.GetString(), callfuncND_selector(ServerListLayer::onPageServerCallBack));
           isLodingData = true;
       }
       else
       {
           tableView->reloadData();
           tablePageView->reloadData();
       }
   }
}
#pragma mark HTTP工具方法
// 发送请求
void ServerListLayer::sendHTTP(std::string url,std::string postContent,SEL_CallFuncND  callBack){
    cocos2d::network::HttpRequest* request = new cocos2d::network::HttpRequest();
    request->setUrl(url.c_str());
    CCLOG("url.c_str() = %s",url.c_str());
    CCLOG("send data = %s",postContent.c_str());
    request->setRequestType(cocos2d::network::HttpRequest::Type::POST);
    std::vector<std::string> headers;
    headers.push_back("Content-Type: application/json; charset=utf-8");
    request->setHeaders(headers);
    request->setResponseCallback(this, callBack);
    
    // write the post data
    const char* postData = postContent.c_str();
    request->setRequestData(postData, strlen(postData));
    
    request->setTag("POST test2");
    cocos2d::network::HttpClient::getInstance()->send(request);
    request->release();
}

void ServerListLayer::onPageServerCallBack(Node *sender, void *data)
{
    CCLOG("收到服务器分页数据");
    isLodingData = false;
    cocos2d::network::HttpResponse *response = (cocos2d::network::HttpResponse*)data;
    if(response->getResponseCode() == 200){
        std::vector<serverListType> serverlistData;
        std::vector<char> *buffer = response->getResponseData();
        std::string bufs(buffer->begin(),buffer->end());
        CCLOG("收到服务器分页数据:%s",bufs.c_str());
        rapidjson::Document d1;
        d1.Parse<0>(bufs.c_str());
        const rapidjson::Value &pArray = d1["slist"];
        for (rapidjson::SizeType i = 0; i < pArray.Size(); i++){
            const rapidjson::Value &valueEnt = pArray[i];
            
            const rapidjson::Value &jsid = valueEnt["sid"];
            std::string sid = StringUtils::format("%d",jsid.GetInt());
            const rapidjson::Value &jsname = valueEnt["sname"];
            std::string sname = jsname.GetString();
            const rapidjson::Value &jstatus = valueEnt["status"];
            int status = jstatus.GetInt();
            const rapidjson::Value &jdesc = valueEnt["desc"];
            std::string sdesc = "";
            if(!jdesc.IsNull()){
                sdesc = jdesc.GetString();
            }
            const rapidjson::Value &JserverIP = valueEnt["serverIP"];
            std::string serverIP = JserverIP.GetString();
            
            
            const rapidjson::Value &Juid = valueEnt["uid"];
            std::string uid = Juid.GetString();
            const rapidjson::Value &Jver = valueEnt["ver"];
            int ver = Jver.GetInt();
            
            CCLOG("解析完的服务器列表 %s:%d:%s:%s",sname.c_str(),status,sid.c_str(),sdesc.c_str());
            /*
             struct serverListType{
             int status;
             std::string sname;
             std::string sid;
             std::string desc;
             std::string serverIP;
             std::string serverPort;
             std::string uid;
             int ver;
             };
             */
            //获取已经登录过的服务器列表
            serverListType slist;
            slist.status = status;
            slist.sname = sname;
            slist.sid = sid;
            slist.desc = sdesc;
            slist.serverIP = serverIP;
            slist.uid = uid;
            slist.ver = ver;
            serverlistData.push_back(slist);
            
        }
        
        this->sServerPageList->at(this->indexPage).serverlist = serverlistData;
        
    }
    tableView->reloadData();
    tablePageView->reloadData();

    
}
Size ServerListLayer::tableCellSizeForIndex(TableView *table, ssize_t idx)
{
    if (table == tableView)
    {
        return Size(470, 75);
    }
    else if (table == tablePageView)
    {
        return Size(190, 80);
    }
    return Size::ZERO;
}

TableViewCell* ServerListLayer::tableCellAtIndex(TableView *table, ssize_t idx)
{
    auto string = String::createWithFormat("%ld", idx);
    int index = string->intValue();
    TableViewCell *cell = table->dequeueCell();
    if (!cell) {
        cell = new TableViewCell();
        cell->autorelease();
    }
    else
    {
        cell->removeAllChildren();
    }

    if (table == tableView) {
        auto list = this->sServerPageList->at(this->indexPage).serverlist;
        int sizeList = list.size() - 1;
        int addidx = (idx != 0 && sizeList%2 == 0) ? 1 : 0;
        for(int i = 0;i<2;i++)
        {
            if (idx == 0 && sizeList%2 == 0 && i == 1) {
                break;
            }
            int index = (idx * 2 + i - addidx);
            std::string strSState = "";
            Color3B color;
            if(list[index].status == 5) {
                strSState = "fwq_yu.png";
                color = SERVER_NAME_COLOR_ST5;
            }else if(list[index].status == 4) {
                //1推2热3流4新0维
                strSState = "dl_xin.png";
                color = SERVER_NAME_COLOR_ST4;
            }else if(list[index].status == 3) {
                strSState = "fwq_liu.png";
                color = SERVER_NAME_COLOR_ST3;
            }else if(list[index].status == 2) {
                strSState = "dl_re.png";
                color = SERVER_NAME_COLOR_ST2;
            }else if(list[index].status == 1) {
                strSState = "fwq_tui.png";
                color = SERVER_NAME_COLOR_ST1;
            }else if(list[index].status == 0) {
                strSState = "fwq_wei.png";
                color = SERVER_NAME_COLOR_ST0;
            }
            
            if(strSState.compare("")!=0){
                auto sprite = Sprite::createWithSpriteFrameName(strSState.c_str());
                sprite->setAnchorPoint(Point(0,0.5));
                sprite->setPosition(Point(i * 220, 75/2));
                sprite->setTag(203);
                cell->addChild(sprite);
            }
            
            //        auto label = Label::createWithSystemFont(string->getCString(), "Helvetica", 20.0);
            //        label->setPosition(Point::ZERO);
            //		label->setAnchorPoint(Point(0.5, 0.5));
            //        label->setTag(123);
            //        cell->addChild(label);
            //
            //        index = string->intValue();
            
            auto labelName = Label::createWithSystemFont(list[index].sname, FONENAME, 25);
            labelName->setPosition(Point(55 + i * 220,75/2));
            labelName->setAnchorPoint(Point(0, 0.5));
            labelName->setTag(200);
            labelName->setColor(color);
            cell->addChild(labelName);
            
            /*
            //1推2热3流4新0维
            std::string strsName[] = {
                WPlatform::getInstance()->getTipInfo(604357),
                WPlatform::getInstance()->getTipInfo(604358),
                WPlatform::getInstance()->getTipInfo(604359),
                WPlatform::getInstance()->getTipInfo(604360),
                WPlatform::getInstance()->getTipInfo(604361),
                "",
                "",
                "",
                ""
            };
            auto labelNameSt = Label::createWithSystemFont(strsName[list[idx * 2 + i].status], FONENAME, 25);
            labelNameSt->setPosition(Point(350 + i * 220,75/2));
            labelNameSt->setAnchorPoint(Point(0, 0.5));
            labelNameSt->setColor(color);
            labelNameSt->setTag(201);
            cell->addChild(labelNameSt);
             */
        }
    }
    
    else if (table == tablePageView)
    {
        int lengt = this->sServerPageList->size() - 1;
        if (lengt - idx == this->indexPage) {
            Sprite *imgHua = Sprite::createWithSpriteFrameName("ty_bianqian_1.png");
            imgHua->setAnchorPoint(Point(0,0));
            imgHua->setPosition(Point(0,-10));
            cell->addChild(imgHua);
        }
        else
        {
            Sprite *imgHua = Sprite::createWithSpriteFrameName("ty_bianqian_2.png");
            imgHua->setAnchorPoint(Point(0,0));
            imgHua->setPosition(Point(10,-10));
            cell->addChild(imgHua);
        }
        auto labelNameSt = Label::createWithSystemFont(this->sServerPageList->at(lengt - idx).pageName, FONENAME, 25);
        labelNameSt->setPosition(Point(80,40));
        labelNameSt->setAnchorPoint(Point(0.5, 0.5));
        labelNameSt->setColor(Color3B(255,255,255));
        cell->addChild(labelNameSt);

    }
    return cell;
}

ssize_t ServerListLayer::numberOfCellsInTableView(TableView *table)
{
    if (table == tableView)
    {
        int lengt = (this->sServerPageList->at(this->indexPage).serverlist).size() + 1;
        
        CCLOG("lengt = %d",int(lengt/2));
        return int(lengt/2);
    }
    else
    {
        int lengt = this->sServerPageList->size();
        return lengt;
    }
    return 0;
}

